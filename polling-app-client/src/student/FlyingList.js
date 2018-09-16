import React, { Component } from 'react';
import ReactTable from "react-table";
import { withRouter } from 'react-router-dom';
import "react-table/react-table.css";
import 'react-confirm-alert/src/react-confirm-alert.css'
import { API_BASE_URL } from '../constants';
import { CSVLink } from 'react-csv';



class FlyingList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            students: [],
            expense: [],
            studentId: 0,

            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false
        };

    }




    fetchCars = () => {
        const token = sessionStorage.getItem("jwt");
        fetch(API_BASE_URL + '/flyingList/',
            {
                headers: { 'Authorization': token }
            })
            .then((response) => response.json())
            .then((responseData) => {
                this.setState({
                    students: responseData,
                });
            })
            .catch(err => console.error(err));
    }






    componentWillMount() {
        this.fetchCars();
    }






    render() {


        const columns = [{
            Header: 'Ref No',
            accessor: 'referenceNo',

        }, {
            Header: 'Call Sign',
            accessor: 'studentName',

        }, {
            Header: 'C150',
            accessor: 'c150',
            filterable: false,

        }, {
            Header: 'C 172 RG',
            accessor: 'c172rg',
            filterable: false,

        }, {
            Header: 'C172 N',
            accessor: 'c172n',
            filterable: false,
        }, {
            Header: 'PA 34',
            accessor: 'pa34',
            filterable: false,

        }, {
            Header: 'ELITE SINGLE',
            accessor: 'eliteSingle',
            filterable: false,

        }, {
            Header: 'ELITE TWIN ',
            accessor: 'eliteTwin',
            filterable: false,

        }, {
            Header: 'FRASCA 141',
            accessor: 'frasca141',
            filterable: false,

        }]






        return (
            <div>
                <CSVLink data={this.state.students} separator=",">Export CSV</CSVLink>


                <ReactTable data={this.state.students} columns={columns}
                    filterable={true} pageSize={20} />

            </div>


        );
    }
}

export default withRouter(FlyingList);