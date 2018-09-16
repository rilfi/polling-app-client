import React, { Component } from 'react';
import ReactTable from "react-table";
import { getAllStudents, createStudent } from '../util/APIUtils';
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
import SumeryExpense from './SumeryExpense';



class StudentList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            students: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false
        };
        
    }

    addCar(car) {
        console.log(car);
      createStudent(car)
        .then(res => this.fetchCars())
        .catch(err => console.error(err))
      } 


    fetchCars = () => {
        const token = sessionStorage.getItem("jwt");
        fetch(API_BASE_URL + '/students', 
        {
          headers: {'Authorization': token}
        })
        .then((response) => response.json()) 
        .then((responseData) => { 
          this.setState({ 
            students: responseData.content,
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

    
  componentDidMount() {
    this.fetchCars();
  }






    render() {

        const columns = [ {
            Header: 'name',
            accessor: 'name',
            Cell: this.renderEditable
           
          }, {
            Header: 'email',
            accessor: 'email',
            Cell: this.renderEditable
            
          }, {
            Header: 'Reference No',
            accessor: 'referenceNo',
            Cell: this.renderEditable
                        
          },{
            Header: 'Contact NO',
            accessor: 'contactNo',
            Cell: this.renderEditable
                        
          }, {
            id: 'savebutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({row}) => (<Button size="small"   onClick={()=>{this.updateCar(row)}}>Save</Button>)
          },  {
            id: 'addexpensebutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({row}) => (<Link to={`/summery/${row._original.id}`} > Statement</Link>)
          }]

         

      


        return (
            <div>
            <NewStudent addCar={this.addCar} fetchCars={this.fetchCars}/>            
<ReactTable data={this.state.students} columns={columns} 
            filterable={true} pageSize={100}/>

            </div>
            
     
        );
    }
}

export default withRouter(StudentList);