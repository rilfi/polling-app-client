import React, { Component } from 'react';
import ReactTable from "react-table";
import { getAllExpenseTypes, createExpenseType ,createExpenseCharge} from '../util/APIUtils';
import { Button, Icon, notification } from 'antd';
import { POLL_LIST_SIZE } from '../constants';
import { withRouter ,Link} from 'react-router-dom';
import '../poll/PollList.css';
import "react-table/react-table.css";
import NewExpense from './NewExpense';
import NewExpenseType from './NewExpenseType';
import {API_BASE_URL} from '../constants';
import { confirmAlert } from 'react-confirm-alert';
import NewCharge from './NewCharge';



class ExpenseTypes extends Component {
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
        createExpenseType(car)
        .then(res => this.fetchCars())
        .catch(err => console.error(err))
      } 
      addCharge(charge,expenseTypeId) {
        console.log(charge);
        createExpenseCharge(charge,expenseTypeId)
        .catch(err => console.error(err))
      } 


    fetchCars = () => {
        const token = sessionStorage.getItem("jwt");
        fetch(API_BASE_URL + '/expenseType', 
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
          console.log(link)
        const token = sessionStorage.getItem("jwt");
        let url;
        url=API_BASE_URL+"/expenseType/"+link._original.id
        fetch(url, 
          { 
            method: 'DELETE',
            headers: {'Authorization': token}
          }
        )
        .then(res => {
          this.setState({open: true, message: 'expenseType deleted'});
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
        console.log(car._original)
        let link;
        link=API_BASE_URL+"/expenseType/"+car._original.id
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
            Header: 'Type',
            accessor: 'type',
           
          }, {
            Header: 'Subtype',
            accessor: 'subType',
            
          }, {
            Header: 'CHARGE',
            accessor: 'charge',
            Cell: this.renderEditable
            
          }, {
            id: 'savebutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({row}) => (<Button size="small"   onClick={()=>{this.updateCar(row)}}>Save</Button>)
          }, {
            id: 'deletebutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({row}) => (<Button size="small"   onClick={()=>{this.confirmDelete(row)}}>Delete</Button>)
          }]

         

      


        return (
            <div>
            <NewExpenseType addCar={this.addCar} fetchCars={this.fetchCars}/>            
<ReactTable data={this.state.students} columns={columns} 
            filterable={true} pageSize={10}/>

            </div>
            
     
        );
    }
}

export default withRouter(ExpenseTypes);