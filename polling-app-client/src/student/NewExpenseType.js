import React from 'react';
import SkyLight from 'react-skylight';
import { Input, Button, Radio,InputNumber  } from 'antd'
const RadioGroup = Radio.Group;


class NewExpenseType extends React.Component {
    constructor(props) {
        super(props);
        this.state = { type: '', subType: '', charge:1000 };
    }

    handleChange = (event) => {
        this.setState(
            { [event.target.name]: event.target.value }
        );
    }
    handleCharge = (event) => {
        this.setState(
            { charge: event }
        );
    }

    // Save car and close modal form
    handleSubmit = (event) => {
        event.preventDefault();
        var newCar = {
            type: this.state.type, subType: this.state.subType, charge: this.state.charge
        };
        this.props.addCar(newCar);
        this.refs.addDialog.hide();
    }

    cancelSubmit = (event) => {
        event.preventDefault();
        this.refs.addDialog.hide();
    }

    render() {

        let sc=<div></div>
        if(this.state.type!="DYNAMIC"){
            sc=<InputNumber  defaultValue={1000} formatter={value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')} parser={value => value.replace(/\$\s?|(,*)/g, '')} onChange={this.handleCharge}  /> 

        }
        else{
            sc=null
        }

        return (
            <div>
                <SkyLight hideOnOverlayClicked ref="addDialog">
                    <h3>New Expense Type</h3>
                    <form>
                        <RadioGroup name ="type" onChange={this.handleChange}>
                            <Radio value="FLYING">FLYING </Radio>
                            <Radio value="DYNAMIC">DYNAMIC</Radio>
                            <Radio value="NONQUANTIFYABLE">NONQUANTIFYABLE</Radio>
                            <Radio value="QUANTIFYABLE">QUANTIFYABLE</Radio>
                        </RadioGroup>
                        <br />
                        <Input label="SubType" placeholder="SubType" name="subType" onChange={this.handleChange} /><br />
                        {sc}<br />
                        <Button variant="outlined" style={{ marginRight: 10 }} color="primary" onClick={this.handleSubmit}>Save</Button>
                        <Button variant="outlined" color="secondary" onClick={this.cancelSubmit}>Cancel</Button>
                    </form>
                </SkyLight>
                <div>
                    <Button variant="raised" color="primary" style={{ 'margin': '10px' }} onClick={() => this.refs.addDialog.show()}>New Expense Type</Button>
                </div>
            </div>
        );
    }
}

export default NewExpenseType;