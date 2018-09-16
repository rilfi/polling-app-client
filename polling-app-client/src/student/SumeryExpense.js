import React, { Component } from 'react';
import ReactTable from "react-table";
import { getAllStudents, createStudent, createdynamic, createQuantifyable, createnonQuantifyablee, createmiscellaneous } from '../util/APIUtils';
import { Button, Icon, notification } from 'antd';
import { POLL_LIST_SIZE } from '../constants';
import { withRouter ,Link} from 'react-router-dom';
import '../poll/PollList.css';
import "react-table/react-table.css";
import NewExpense from './NewExpense';
import NewStudent from './NewStudent';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css' 
import {API_BASE_URL} from '../constants';
import NewDiscount from './NewDiscount';



class SumeryExpense extends Component {
    constructor(props) {
        super(props);
        this.state = {
          students:[],
          expense: [],
          studentId:0,

            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false
        };
        
    }

    SelectWhichType(expense){

      if(this.expense.expenseTypeId==1){
        this.adddynamic()

      }
      else if(this.expense.expenseTypeId==2){
        this.addmiscellaneous()
      }
     
      else if(this.expense.expenseTypeId==4){
        this.addnonQuantifyable()
      }
      else if(this.expense.expenseTypeId==1){
        this.addquantifiable()
      }

    }

    adddynamic() {
      console.log(this.expense);
      createdynamic(this.expense,this.studentId)
      .then(res => this.fetchCars())
      .catch(err => console.error(err))
    } 
    addquantifiable() {
      console.log(this.expense);
    createQuantifyable(this.expense,this.studentId)
      .then(res => this.fetchCars())
      .catch(err => console.error(err))
    } 
    addnonQuantifyable() {
      console.log(this.expense);
    createnonQuantifyablee(this.expense,this.studentId)
      .then(res => this.fetchCars())
      .catch(err => console.error(err))
    } 
    addmiscellaneous() {
      console.log(this.expense);
      console.log(this.studentId)
    createmiscellaneous(this.expense,this.studentId)
      .then(res => this.fetchCars())
      .catch(err => console.error(err))
    } 


    fetchCars = () => {
        const token = sessionStorage.getItem("jwt");
        fetch(API_BASE_URL + '/statement/'+this.props.match.params.id, 
        {
          headers: {'Authorization': token}
        })
        .then((response) => response.json()) 
        .then((responseData) => { 
          this.setState({ 
            students: responseData,
          }); 
        })
        .catch(err => console.error(err));  
      }

    confirmDelete = (link) => {
        confirmAlert({
          message: 'Are you sure to delete?',
          buttons: [
            {
              label: 'Yes',
              onClick: () => this.onDelClick(link)
            },
            {
              label: 'No',
            }
          ]
        })
      }
    
      // Delete car
      onDelClick = (link) => {
        const token = sessionStorage.getItem("jwt");
        let url;
        url=API_BASE_URL+"/student/"+link._original.id
        fetch(url, 
          { 
            method: 'DELETE',
            headers: {'Authorization': token}
          }
        )
        .then(res => {
          this.setState({open: true, message: 'Student deleted'});
          this.fetchCars();
        })
        .catch(err => {
          this.setState({open: true, message: 'Error when deleting'});
          console.error(err)
        }) 
      }
      
  
    
      // Update car
      updateCar(car) {
        const token = sessionStorage.getItem("jwt");
        //console.log(link)
        let link;
        link=API_BASE_URL+"/student/"+car._original.id
        console.log(car._original)
        fetch(link, 
        { method: 'PUT', 
          headers: {
            'Content-Type': 'application/json',
            'Authorization': token
          },
          body: JSON.stringify(car._original)
        })
        .then( res =>
          this.setState({open: true, message: 'Changes saved'})
        )
        .catch( err => 
          this.setState({open: true, message: 'Error when saving'})
        )
      }

 
      renderEditable = (cellInfo) => {
        return (
          <div
            style={{ backgroundColor: "#fafafa" }}
            contentEditable
            suppressContentEditableWarning
            onBlur={e => {
              const data = [...this.state.students];
              data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
              this.setState({ students: data });
            }}
            dangerouslySetInnerHTML={{
              __html: this.state.students[cellInfo.index][cellInfo.column.id]
            }}                
          />
        );
      }  
    
      handleClose = (event, reason) => {
        this.setState({ open: false });
      };

      componentWillMount(){
        this.setState({studentId:this.props.match.params.id})

      }

    
  componentDidMount() {
    this.fetchCars();
    this.setState({studentId:this.props.match.params.id})
  }






    render() {
      console.log("render")

      console.log(this.state)


        const columns = [ {
            Header: 'Date',
            accessor: 'date',
           
          }, {
            Header: 'discription',
            accessor: 'discription',
            width:500
            
          }, {
            Header: 'expense',
            accessor: 'expense',
                        
          }, {
            Header: 'deposit',
            accessor: 'deposit',
                        
          }]

         

      


        return (
            <div>
                <NewExpense fetchCars={this.fetchCars}  studentId={this.state.studentId}/>

<ReactTable data={this.state.students} columns={columns} 
            filterable={false} pageSize={20}/>

            </div>
            
     
        );
    }
}

export default withRouter(SumeryExpense);