import React from 'react';
import SkyLight from 'react-skylight';
import { InputNumber, Button } from 'antd'


class NewCharge extends React.Component {
    constructor(props) {
        super(props);
        this.state = { expenseTypeId: '', charge: ''};
    }

    handleChange = (event) => {
        this.setState(
            { expenseTypeId:this.props.id , charge: event }
        );
    }

    // Save car and close modal form
    handleSubmit = (event) => {
        event.preventDefault();
        var newCar = {
            expenseTypeId: this.state.expenseTypeId, charge: this.state.charge
        };
        this.props.addCharge(newCar,this.props.id);
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
                    <h3>New Charge</h3>
                    <form>
                        <InputNumber
                            defaultValue={1000}
                            formatter={value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                            parser={value => value.replace(/\$\s?|(,*)/g, '')}
                            onChange={this.handleChange}
                        />
                        
                        <Button color="primary" onClick={this.handleSubmit}>Save</Button>
                        <Button onClick={this.cancelSubmit}>Cancel</Button>
                    </form>
                </SkyLight>
                <div>
                    <Button   onClick={() => this.refs.addDialog.show()}>Charge</Button>
                </div>
            </div>
        );
    }
}

export default NewCharge;