import React from 'react';
import SkyLight from 'react-skylight';
import {Input, Button} from 'antd'


class NewStudent extends React.Component {
  constructor(props) {
      super(props);
      this.state = {name: '', email: '',  index: '',referenceNo:'',contactNo:''};
  }

  handleChange = (event) => {
      this.setState(
          {[event.target.name]: event.target.value}
      );
  }    
  
  // Save car and close modal form
  handleSubmit = (event) => {
      event.preventDefault();
      var newCar = {name: this.state.name, email: this.state.email, 
        referenceNo: this.state.referenceNo,contactNo:this.state.contactNo};
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
          <h3>New Student</h3>
          <form>
            <Input label="Name" placeholder="Name"  name="name" onChange={this.handleChange}/><br/>    
            <Input label="Email" placeholder="Email" name="email" onChange={this.handleChange}/><br/>
            <Input label="Reference No" placeholder="ReferenceNo" name="referenceNo" onChange={this.handleChange}/><br/>
            <Input label="Contact No" placeholder="Contact No" name="contactNo" onChange={this.handleChange}/><br/>

            <Button variant="outlined" style={{marginRight: 10}} color="primary" onClick={this.handleSubmit}>Save</Button>        
            <Button variant="outlined" color="secondary" onClick={this.cancelSubmit}>Cancel</Button>        
          </form>     
        </SkyLight>
        <div>
            <Button variant="raised" color="primary" style={{'margin': '10px'}} onClick={() => this.refs.addDialog.show()}>New Student</Button>
        </div>
      </div>   
    );
  }
}

export default NewStudent;