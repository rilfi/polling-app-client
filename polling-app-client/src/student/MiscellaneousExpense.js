import React, { Component } from 'react';
import {  Input, Button } from 'antd';
import {  createmiscellaneous } from '../util/APIUtils';



class Miscellaneous extends Component {
constructor(props){
    super(props)
    this.state={expenseTypeId:2}
    this.handleSubmit1=this.handleSubmit1.bind(this);
}

handleChange = (event) => {
    this.setState(
        {[event.target.name]: event.target.value}
    );
    console.log(this.state)
} 

handleSubmit1 = (event) => {
    event.preventDefault();
    var newCar = {brand: this.state.brand, model: this.state.model, 
      color: this.state.color, year: this.state.year, price: this.state.price};
    this.addmiscellaneous();
    console.log("miscellaneous")
    console.log(this.props)
    

}

addmiscellaneous = ()=> {
    console.log("addmiscellaneous");
    console.log(this.state);
    console.log(this.props.studentId);
    let miscellaneous1 ={amount: this.state.amount, expenseName: this.state.expenseName, expenseTypeId: this.state.expenseTypeId };

  createmiscellaneous(miscellaneous1,this.props.studentId)
  } 


    render(){

        return (<div>
            <Input label="Expense Name" placeholder="Expense Name" name="expenseName" onChange={this.handleChange} /><br />
            <Input label="Amount" placeholder="Amount" name="amount" onChange={this.handleChange} /><br />
            <Button variant="outlined" style={{ marginRight: 10 }} color="primary" onClick={this.handleSubmit1}>Save</Button>
            <Button variant="outlined" color="secondary" onClick={this.props.cancelSubmit}>Cancel</Button>
        </div>
    
        );

    }



  
}
export default Miscellaneous