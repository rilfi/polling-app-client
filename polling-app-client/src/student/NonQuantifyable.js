import React, { Component } from 'react';
import { Select, Input,Button } from 'antd';
import { API_BASE_URL  } from '../constants';
import {  createnonQuantifyablee } from '../util/APIUtils';

const Option = Select.Option;
class NonQuantifyable extends Component {

    constructor(props) {
        super(props)
        this.state = {
            Dynamictype: [ ]

        }

        this.fetchCars=this.fetchCars.bind(this);


    }

    componentDidMount() {
        this.fetchCars();
    }

    handleChange = (event) => {
        this.setState(
            {dynamicExpence: event}
        );
        console.log(this.state)
    } 
    
    handleSubmit1 = (event) => {
        event.preventDefault();

        this.addmiscellaneous();
        console.log("miscellaneous")
        console.log(this.props)
        
    
    }
    addmiscellaneous = ()=> {
        console.log("addmiscellaneous");
        console.log(this.state);
        console.log(this.props.studentId);
        let miscellaneous1 ={ expenseTypeId: this.state.dynamicExpence };
    
        createnonQuantifyablee(miscellaneous1,this.props.studentId)
      } 

    fetchCars() {
        const token = sessionStorage.getItem("jwt");

        fetch(API_BASE_URL  + '/expenseType/NonQuantifyable',
            {
                headers: { 'Authorization': token }
            })
            .then((response) => response.json())
            .then((responseData) => {
                this.setState({
                    Dynamictype: responseData,
                });
            })
            .catch(err => console.error(err));
    }

    render() {
        let dynamic
        console.log(this.state.Dynamictype)
        dynamic = this.state.Dynamictype.map(v => (
            <Option value={v.id}>{v}</Option>
        ));
        return (<div>
            <Select name="dynamicExpence"  onChange ={this.handleChange } >
                {dynamic}
            </Select><br />
            <Button variant="outlined" style={{ marginRight: 10 }} color="primary" onClick={this.props.handleSubmit}>Save</Button>
        <Button variant="outlined" color="secondary" onClick={this.props.cancelSubmit}>Cancel</Button>
        </div>

        )



    }





}
export default NonQuantifyable