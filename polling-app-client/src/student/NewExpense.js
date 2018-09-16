import React from 'react';
import SkyLight from 'react-skylight';
import { Input, Button, Radio } from 'antd'
import Miscellaneous from './MiscellaneousExpense';
import DynamicExpense from './DynamicExpense';
import { API_BASE_URL } from '../constants';
import NonQuantifyable from './NonQuantifyable';
import Quantifyable from './Quantifyable';




const RadioGroup = Radio.Group;


class NewExpense extends React.Component {
    constructor(props) {
        super(props);
        this.state = { studentId:'',subType:''};

    }

    handleChange = (event) => {
        console.log("event")
        console.log(event)
        console.log(this.state)
        this.setState(
            {[event.target.name]: event.target.value}
        );
        //console.log(this.state)
    } 
    handleSelect = (event) => {
        this.setState(
            {subType: event}
        );
       // console.log(this.state)
    }



   

    // Save car and close modal form
    handleSubmit = () => {
        console.log("handleSubmit")
        this.props.fetchCars;    
        this.refs.addDialog.hide();    
    }

    cancelSubmit = (event) => {
        event.preventDefault();    
        this.refs.addDialog.hide();    
      }
      componentDidMount(){
          this.setState({
              studentId:this.props.studentId
          })
      }
      

    render() {

        let sc=<div></div>
        if(this.state.expenseTypeId==2){
            sc=<Miscellaneous handleChange={this.handleChange} handleSubmit={this.handleSubmit}  cancelSubmit={this.cancelSubmit}  studentId={this.state.studentId} />
        }
        else if(this.state.expenseTypeId==3){
            sc=<NonQuantifyable  handleChange={this.handleChange} handleSubmit={this.handleSubmit}  cancelSubmit={this.cancelSubmit} />
        }
        else if(this.state.expenseTypeId==1){
            sc=<DynamicExpense  handleChange={this.handleChange} handleSubmit={this.handleSubmit}  cancelSubmit={this.cancelSubmit}/>
        }
        else if(this.state.expenseTypeId==4){
            sc=<Quantifyable  handleChange={this.handleChange} handleSubmit={this.handleSubmit}  cancelSubmit={this.cancelSubmit}/>
        }
        




        return (
            <div>

                <SkyLight hideOnOverlayClicked ref="addDialog">
                    <h3>New Expense</h3>
                    <form>
                        <RadioGroup name="expenseTypeId" buttonStyle="solid" onChange={this.handleChange}  >
                        <Radio.Button key={2} value={2}>Miscellaneous</Radio.Button>
                        <Radio.Button key={1} value={1}>Dynamic</Radio.Button>
                        <Radio.Button key={3} value={3}>NonQuantifyable</Radio.Button>
                        <Radio.Button key={4} value={4}>Quantifyable</Radio.Button>
                        </RadioGroup>
                        <br />
                        {sc}
                   
                    </form>
                </SkyLight>
                <div>
                    <Button onClick={() => this.refs.addDialog.show()}>Invoice</Button>
                </div>
            </div>
        );
    }
}




export default NewExpense;