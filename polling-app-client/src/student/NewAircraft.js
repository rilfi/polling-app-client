import React from 'react';
import SkyLight from 'react-skylight';
import {Input, Button,Select} from 'antd'
import {API_BASE_URL} from '../constants';
const Option = Select.Option;



class NewAircraft extends React.Component {
  constructor(props) {
      super(props);
      this.state = {name: '', aircraftTypeId: '', aircraftTypes:[]};

    }

  handleChange = (event) => {
      this.setState(
          {[event.target.name]: event.target.value}
      );
  }  
  handleSelect = (event) => {
    this.setState(
        {aircraftTypeId: event}
    );
}  
  
  // Save car and close modal form
  handleSubmit = (event) => {
      event.preventDefault();
      var newCar = {name: this.state.name, aircraftTypeId: this.state.aircraftTypeId};
      this.props.addCar(newCar);    
      this.refs.addDialog.hide();    
  }

  cancelSubmit = (event) => {
    event.preventDefault();    
    this.refs.addDialog.hide();    
  }
  
  fetchCars() {
    const token = sessionStorage.getItem("jwt");

    fetch(API_BASE_URL  + '/aircraftType',
        {
            headers: { 'Authorization': token }
        })
        .then((response) => response.json())
        .then((responseData) => {
            this.setState({
                aircraftTypes: responseData
            });
        })
        .catch(err => console.error(err));

        console.log(this.state)
}
componentDidMount() {
    this.fetchCars();
}
  
  render() {
      

    let aircraftTypeOptions
    console.log("this.state")

        console.log(this.state)
         aircraftTypeOptions = this.state.aircraftTypes.map(v => (
            <Option value={v.id}>{v.type}</Option>
        )); 
        console.log(aircraftTypeOptions)
    return (
      <div>
        <SkyLight hideOnOverlayClicked ref="addDialog">
          <h3>New Aircraft</h3>
          <form>
          <Select name="aircraftType" onChange={this.handleSelect} >
                {aircraftTypeOptions}
            </Select><br />
            <Input label="Name" placeholder="Name"  name="name" onChange={this.handleChange}/><br/>  

           
            <Button variant="outlined" style={{marginRight: 10}} color="primary" onClick={this.handleSubmit}>Save</Button>        
            <Button variant="outlined" color="secondary" onClick={this.cancelSubmit}>Cancel</Button>        
          </form>     
        </SkyLight>
        <div>
            <Button variant="raised" color="primary" style={{'margin': '10px'}} onClick={() => this.refs.addDialog.show()}>New Aircraft</Button>
        </div>
      </div>   
    );
  }
}

export default NewAircraft;