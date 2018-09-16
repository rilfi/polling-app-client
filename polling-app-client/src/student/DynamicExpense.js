import React, { Component } from 'react';
import { Select, Input,Button } from 'antd';
import { ACCESS_TOKEN  } from '../constants';

const Option = Select.Option;
class DynamicExpense extends Component {

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

    fetchCars() {
        const token = sessionStorage.getItem("jwt");

        fetch(ACCESS_TOKEN  + '/expenseType/Dynamic',
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
            <Option value={v}>{v}</Option>
        ));
        return (<div>
            <Select name="dynamicExpence" onChange={this.props.handleChange} >
                {dynamic}
            </Select><br />
            <Input label="Amount" placeholder="Amount" name="amount" onChange={this.props.handleChange} /><br />
            <Button variant="outlined" style={{ marginRight: 10 }} color="primary" onClick={this.props.handleSubmit}>Save</Button>
        <Button variant="outlined" color="secondary" onClick={this.props.cancelSubmit}>Cancel</Button>
        </div>

        )



    }





}
export default DynamicExpense