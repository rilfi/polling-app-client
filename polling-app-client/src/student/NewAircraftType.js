import React from 'react';
import SkyLight from 'react-skylight';
import { Input, Button,InputNumber } from 'antd'


class NewAircraftType extends React.Component {
    constructor(props) {
        super(props);
        this.state = { type: '', amount: 1000 };
    }

    handleChange = (event) => {
        console.log(event)
      this.setState(
          { [event.target.name]: event.target.value }
        );
        console.log(this.state)
    }
    handleAmount = (event) => {
        console.log(event)
      this.setState(
          { amount: event}
        );
        console.log(this.state)
    }

    // Save car and close modal form
    handleSubmit = (event) => {
        event.preventDefault();
        var newCar = { type: this.state.type, amount: this.state.amount };
        this.props.addCar(newCar);
        this.refs.addDialog.hide();
    }

    cancelSubmit = (event) => {
        event.preventDefault();
        this.refs.addDialog.hide();
    }

    render() {
        return (
            <div>
                <SkyLight hideOnOverlayClicked ref="addDialog">
                    <h3>New Aircraft Type</h3>
                    <form>
                        <Input label="Type" placeholder="Type" name="type" onChange={this.handleChange} /><br />
                        <InputNumber
                        name="amount"
                            defaultValue={1000}
                            formatter={value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                            parser={value => value.replace(/\$\s?|(,*)/g, '')}
                            onChange={this.handleAmount}
                        />
                        <Button variant="outlined" style={{ marginRight: 10 }} color="primary" onClick={this.handleSubmit}>Save</Button>
                        <Button variant="outlined" color="secondary" onClick={this.cancelSubmit}>Cancel</Button>
                    </form>
                </SkyLight>
                <div>
                    <Button variant="raised" color="primary" style={{ 'margin': '10px' }} onClick={() => this.refs.addDialog.show()}>New Aircraft Type</Button>
                </div>
            </div>
        );
    }
}

export default NewAircraftType;