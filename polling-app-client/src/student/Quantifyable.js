import React, { Component } from 'react';
import { Select, InputNumber,Button  } from 'antd';
import { API_BASE_URL  } from '../constants';

const Option = Select.Option;
class Quantifyable extends Component {

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

        fetch(API_BASE_URL  + '/expenseType/Quantifyable',
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
            <InputNumber min={1} max={10} defaultValue={1} onChange={this.props.handleChange} /><br />
            <Button variant="outlined" style={{ marginRight: 10 }} color="primary" onClick={this.props.handleSubmit}>Save</Button>
        <Button variant="outlined" color="secondary" onClick={this.props.cancelSubmit}>Cancel</Button>
        </div>


        )



    }





}
export default Quantifyable