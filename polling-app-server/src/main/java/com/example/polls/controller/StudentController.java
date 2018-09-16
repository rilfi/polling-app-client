package com.example.polls.controller;


import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.*;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.StudentService;
import com.example.polls.util.AppConstants;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.round;

@RestController
@RequestMapping("/api")
public class StudentController {


    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private SummeryExpenseRepository summeryExpenseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private DynamicExpenseRepository dynamicExpenseRepository;
    @Autowired
    QuantifiableExpenseRepository quantifiableExpenseRepository;

    @Autowired
    private NonQuantifyableExpenseRepository nonQuantifyableExpenseRepository;

    @Autowired
    private FlyingExpenseRepository flyingExpenseRepository;
    @Autowired
    private MiscellaneousExpenseRepository miscellaneousExpenseRepository;

    @Autowired
    private FlyingHoursRepository flyingHoursRepository;
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private AircraftTypeHoursRepository aircraftTypeHoursRepository;

    @GetMapping("/students")
    //@PreAuthorize("hasRole('USER')")
    public PagedResponse<StudentResponce> getStudents(@CurrentUser UserPrincipal currentUser,
                                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return studentService.getAllStudents(currentUser, page, size);
    }

    @GetMapping("/expenseType")
    //@PreAuthorize("hasRole('USER')")
    public List<ExpenseTypeResponce> getExpenseType() {


        return studentService.getExpensetypes();
    }

    @GetMapping("/discounts")
    //@PreAuthorize("hasRole('USER')")
    public List<DiscountsResponse> getDiscounts() {

        List<DiscountsResponse> discountsResponses = discountRepository.findAll().stream().map(discount -> {

            DiscountsResponse discountsResponse = new DiscountsResponse();
            discountsResponse.setStartData(String.valueOf(discount.getStartDate()));
            discountsResponse.setEndDate(String.valueOf(discount.getEndDate()));
            discountsResponse.setStudentName(discount.getStudent().getName());
            discountsResponse.setExpenseType(discount.getExpenseType().getSubType());
            discountsResponse.setAmount(discount.getAmount());
            return discountsResponse;

        }).collect(Collectors.toList());


        return discountsResponses;


    }

    @GetMapping("/expenseType/{type}")
    //@PreAuthorize("hasRole('USER')")
    public List<String> getExpenseType(@PathVariable String type) {
        List<String> expenseSubList = expenseTypeRepository.findAllByType(type).stream().map(expenseType -> expenseType.getSubType()).collect(Collectors.toList());
        return expenseSubList;
    }

    @GetMapping("/flyingHours/{name}")
    //@PreAuthorize("hasRole('USER')")
    public List<FlyingHours> getFlyingHours(@PathVariable String name) {
        List<FlyingHours> flyingHours = flyingHoursRepository.findAllByName(name);
        return flyingHours;
    }

    @GetMapping("/statement/{studentId}")
    //@PreAuthorize("hasRole('USER')")
    public List<StatementResponce> getStatement(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("student", "id", studentId));
        List<StatementResponce> statementResponces = new ArrayList<>();
        List<FlyingCount> flyingCounts = flyingExpenseRepository.getByStudentAndExpenseTypeAndRateAndDiscount(studentId);
        StatementResponce statementResponce = new StatementResponce();

        statementResponce.setDiscription("Flying Feess");
        statementResponces.add(statementResponce);
        for (FlyingCount fh : flyingCounts) {
            StatementResponce sr = new StatementResponce();
            String discription = fh.getSubType() + ": rate(" + fh.getRate() + ")- Discount per Hour(" + fh.getDiscount() + ") X Duration(" + fh.getDurationCount() + ")";
            sr.setDiscription(discription);
            sr.setExpense(fh.getSumAmount());
            statementResponces.add(sr);
        }
        List<MiscellaneousExpense> miscellaneousExpenses = miscellaneousExpenseRepository.findAllByStudent_Id(studentId);
        StatementResponce msr = new StatementResponce();
        msr.setDiscription("Miscellaneous Expense");
        statementResponces.add(msr);
        for (MiscellaneousExpense me : miscellaneousExpenses) {
            StatementResponce sr = new StatementResponce();
            sr.setDiscription(me.getExpenseName());
            sr.setExpense(me.getAmount());
            statementResponces.add(sr);
        }


        return statementResponces;
    }


    @PostMapping("/students")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        Student student = studentService.createStudent(studentRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "student Created Successfully"));
    }

    @PostMapping("/expenseType")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createExpenseType(@Valid @RequestBody ExpenseTypeRequest expenseTypeRequest) {
        ExpenseType expenseType = new ExpenseType();
        expenseType.setType(expenseTypeRequest.getType().replaceFirst("\\s++$", ""));
        expenseType.setSubType(expenseTypeRequest.getSubType().toUpperCase().replaceFirst("\\s++$", ""));
        expenseType.setCharge(expenseTypeRequest.getCharge());
        expenseType = expenseTypeRepository.save(expenseType);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{expenseTypeId}")
                .buildAndExpand(expenseType.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "expenseType Created Successfully"));
    }


    @PostMapping("/miscellaneous/{studentId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addMiscellaneousExpense(@PathVariable Long studentId,
                                                     @Valid @RequestBody MiscellaneousExpenseRequest miscellaneousExpenseRequest) {

        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));
        MiscellaneousExpense miscellaneousExpense = new MiscellaneousExpense();
        miscellaneousExpense.setStudent(student);
        miscellaneousExpense.setExpenseName(miscellaneousExpenseRequest.getExpenseName());
        miscellaneousExpense.setAmount(miscellaneousExpenseRequest.getAmount());
        miscellaneousExpense = miscellaneousExpenseRepository.save(miscellaneousExpense);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{miscellaneousExpenseId}")
                .buildAndExpand(miscellaneousExpense.getId()).toUri();


        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "miscellaneousExpense Created Successfully"));


    }

    @GetMapping("/summeryexpense/{studentId}")
        // @PreAuthorize("hasRole('USER')")
    List<SummeryExpenseResponce> getSummeryExpense(@PathVariable Long studentId) {
        return studentService.getSummeryExpense(studentId);
    }

  /*  @GetMapping("/expense/{studentId}")
        // @PreAuthorize("hasRole('USER')")
    List<ExpenseResponse> getExpense(@PathVariable Long studentId){
        List<Expense> expenses=expenseRepository.findAllByStudent_Id(studentId);
        List<ExpenseResponse> expenseResponses = expenses.stream().map(expense ->
        {
            ExpenseResponse obj = new ExpenseResponse();
            obj.setId(expense.getId());
            obj.setExpenceType(expense.getExpenseType().getType());
            obj.setSubType(expense.getExpenseType().getSubType());
            obj.setQuantity(expense.getQuantity());
            obj.setDescription(expense.getDescription());
            obj.setAmount(expense.getAmount());
            obj.setCreationDateTime(expense.getCreatedAt());
            return obj;
        }).collect(Collectors.toList());
        return expenseResponses;
    }*/

    @DeleteMapping("student/{studentId}")
    // @PreAuthorize("hasRole('USER')")
    public void deleteStudent(@PathVariable Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @DeleteMapping("expenseType/{expenseTypeId}")
    // @PreAuthorize("hasRole('USER')")
    public void deleteExpenseType(@PathVariable Long expenseTypeId) {
        expenseTypeRepository.deleteById(expenseTypeId);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id) {

        Optional<Student> studentOptional = studentRepository.findById(id);

        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();

        student.setId(id);

        studentRepository.save(student);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/expenseType/{id}")
    public ResponseEntity<Object> updateExpenseType(@RequestBody ExpenseType expenseType, @PathVariable long id) {

        Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findById(id);

        if (!expenseTypeOptional.isPresent())
            return ResponseEntity.notFound().build();

        expenseType.setId(id);


        expenseTypeRepository.save(expenseType);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/dynamic/{studentId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createDynamic(@Valid @RequestBody DynamicExpenseRequest dynamicExpenseRequest, @PathVariable Long studentId) {
        DynamicExpense dynamicExpense = new DynamicExpense();
        dynamicExpense.setAmount(dynamicExpenseRequest.getAmount());
        ExpenseType expenseType = expenseTypeRepository.findById(dynamicExpenseRequest.getExpenseTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("expenseType", "id", dynamicExpenseRequest.getExpenseTypeId()));
        dynamicExpense.setExpenseType(expenseType);
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("expenseType", "id", dynamicExpenseRequest.getStudentId()));
        dynamicExpense.setStudent(student);

        dynamicExpense = dynamicExpenseRepository.save(dynamicExpense);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(dynamicExpense.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "charge Created Successfully"));
    }

    @PostMapping("/nonQuantifyable/{studentId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createNonQuantifyable(@Valid @RequestBody NonQuantifyableExpenseRequest nonQuantifyableExpenseRequest, @PathVariable Long studentId) {
        NonQuantifyableExpense nonQuantifyableExpense = new NonQuantifyableExpense();
        ExpenseType expenseType = expenseTypeRepository.findById(nonQuantifyableExpenseRequest.getExpenseTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("expenseType", "id", nonQuantifyableExpenseRequest.getExpenseTypeId()));
        nonQuantifyableExpense.setExpenseType(expenseType);
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("expenseType", "id", nonQuantifyableExpenseRequest.getStudentId()));
        nonQuantifyableExpense.setStudent(student);

        nonQuantifyableExpense = nonQuantifyableExpenseRepository.save(nonQuantifyableExpense);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(nonQuantifyableExpense.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "charge Created Successfully"));
    }

    @PostMapping("/quantifyable/{studentId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createQuantifyable(@Valid @RequestBody QuantifiableExpenseRequest quantifiableExpenseRequest, @PathVariable Long studentId) {
        QuantifiableExpense quantifiableExpense = new QuantifiableExpense();
        ExpenseType expenseType = expenseTypeRepository.findById(quantifiableExpenseRequest.getExpenseTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("expenseType", "id", quantifiableExpenseRequest.getExpenseTypeId()));
        quantifiableExpense.setExpenseType(expenseType);
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("expenseType", "id", quantifiableExpenseRequest.getStudentId()));
        quantifiableExpense.setStudent(student);
        quantifiableExpense.setQuantity(quantifiableExpenseRequest.getQuantity());

        quantifiableExpense = quantifiableExpenseRepository.save(quantifiableExpense);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(quantifiableExpense.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "charge Created Successfully"));
    }
    @GetMapping("/flyingList")
    public List<AircraftTypeHoursResponce>getAircraftHours(){
        List<AircraftTypeHoursResponce>aircraftTypeHoursResponces=aircraftTypeHoursRepository.findAll().stream().map(aircraftTypeHours -> {
            AircraftTypeHoursResponce ar=new AircraftTypeHoursResponce();
            ar.setReferenceNo(aircraftTypeHours.getStudent().getReferenceNo());
            ar.setStudentName(aircraftTypeHours.getStudent().getName());
            ar.setC150(Precision.round(aircraftTypeHours.getC150(),2));
            ar.setC172n(Precision.round(aircraftTypeHours.getC172n(),2));
            ar.setC172rg(Precision.round(aircraftTypeHours.getC172rg(),2));
            ar.setPa34(Precision.round(aircraftTypeHours.getPa34(),2));
            ar.setFrasca141(Precision.round(aircraftTypeHours.getFrasca141(),2));
            ar.setEliteSingle(Precision.round(aircraftTypeHours.getEliteSingle(),2));
            ar.setEliteTwin(Precision.round(aircraftTypeHours.getEliteTwin(),2));
            return ar;

        }).collect(Collectors.toList());
        return aircraftTypeHoursResponces;

    }


    @PostMapping("/flyingExpense")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createFlyingExpense(@Valid @RequestBody FlyingExpenseRequest flyingExpenseRequest) {


        LocalDate localDate = LocalDate.parse(flyingExpenseRequest.getDate());
        Student student = studentRepository.findByReferenceNo(flyingExpenseRequest.getStudentReference());
        Date d1 = null;
        try {
            d1 = new SimpleDateFormat("yyyy-MM-dd").parse(flyingExpenseRequest.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ExpenseType expenseType = expenseTypeRepository.findByTypeAndSubType("FLYING", flyingExpenseRequest.getAircraftType());
        Discount discount=null;
        try {
            discount = discountRepository.getByStudentIdAndExpenseTypeIdAndStartDateBeforeAndEndDateAfter(student.getId(), expenseType.getId(), d1);
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println(flyingExpenseRequest);
        }

        FlyingExpense flyingExpense = new FlyingExpense();


        flyingExpense.setDate(d1);
        flyingExpense.setStudent(student);
        flyingExpense.setExpenseType(expenseType);
        flyingExpense.setDuration(flyingExpenseRequest.getDuration());
        flyingExpense.setDiscount(0.0);
        if (discount != null) {
            flyingExpense.setDiscount(discount.getAmount());

        }
        flyingExpense.setRate(expenseType.getCharge());
        flyingExpense.setAmount((flyingExpense.getRate() - flyingExpense.getDiscount() * flyingExpense.getDuration()));
        flyingExpense = flyingExpenseRepository.save(flyingExpense);

        AircraftTypeHours aircraftTypeHours=aircraftTypeHoursRepository.findByStudent_Id(student.getId());
        if(aircraftTypeHours==null){
            aircraftTypeHours=new AircraftTypeHours();
            aircraftTypeHours.setStudent(student);

            switch (flyingExpenseRequest.getAircraftType()){
                case "C150":
                    aircraftTypeHours.setC150(flyingExpenseRequest.getDuration());
                    break;
                case "C172RG":
                    aircraftTypeHours.setC172rg(flyingExpenseRequest.getDuration());
                case "PA34":
                    aircraftTypeHours.setPa34(flyingExpenseRequest.getDuration());
                    break;
                case "C172N":
                    aircraftTypeHours.setC172n(flyingExpenseRequest.getDuration());
                case "C152":
                    aircraftTypeHours.setC152(flyingExpenseRequest.getDuration());
                    break;
                case "ELITEFNPT2SINGLE ":
                    aircraftTypeHours.setEliteSingle(flyingExpenseRequest.getDuration());
                    break;
                case "ELITEFNPT2TWIN ":
                    aircraftTypeHours.setC172rg(flyingExpenseRequest.getDuration());
                    break;



            }
        }
        else {
            Double duration=0.0;

            switch (flyingExpenseRequest.getAircraftType()){
                case "C150":
                    duration=aircraftTypeHours.getC150()==null?0.0:aircraftTypeHours.getC150();

                    aircraftTypeHours.setC150(duration+flyingExpenseRequest.getDuration());
                    break;
                case "C172RG":
                    duration=aircraftTypeHours.getC172rg()==null?0.0:aircraftTypeHours.getC172rg();

                    aircraftTypeHours.setC172rg(duration+flyingExpenseRequest.getDuration());
                case "PA34":
                    duration=aircraftTypeHours.getPa34()==null?0.0:aircraftTypeHours.getPa34();

                    aircraftTypeHours.setPa34(duration+flyingExpenseRequest.getDuration());
                    break;
                case "C172N":
                    duration=aircraftTypeHours.getC172n()==null?0.0:aircraftTypeHours.getC172n();

                    aircraftTypeHours.setC172n(duration+flyingExpenseRequest.getDuration());
                case "C152":
                    duration=aircraftTypeHours.getC152()==null?0.0:aircraftTypeHours.getC152();

                    aircraftTypeHours.setC152(duration+flyingExpenseRequest.getDuration());
                    break;
                case "ELITEFNPT2SINGLE":
                    duration=aircraftTypeHours.getEliteSingle()==null?0.0:aircraftTypeHours.getEliteSingle();

                    aircraftTypeHours.setEliteSingle(duration+flyingExpenseRequest.getDuration());
                    break;
                case "ELITEFNPT2TWIN":
                    duration=aircraftTypeHours.getEliteTwin()==null?0.0:aircraftTypeHours.getEliteTwin();

                    aircraftTypeHours.setEliteTwin(duration+flyingExpenseRequest.getDuration());
                    break;
                case "FRASCA141":
                    duration=aircraftTypeHours.getFrasca141()==null?0.0:aircraftTypeHours.getFrasca141();

                    aircraftTypeHours.setFrasca141(duration+flyingExpenseRequest.getDuration());
                    break;




            }

        }


        aircraftTypeHoursRepository.save(aircraftTypeHours);




        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{flyingExpenseId}")
                .buildAndExpand(flyingExpense.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "flyingExpense Created Successfully"));
    }


}
