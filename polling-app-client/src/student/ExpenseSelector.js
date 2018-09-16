import React, { Component } from 'react';
import { Select } from 'antd';
const Option = Select.Option;


class ExpenseSelector extends Component {
  



    rander() {





        return (

            <div>
                <Select defaultValue="lucy" style={{ width: 120 }}>
                    <Option value="jack">Jack</Option>
                    <Option value="lucy">Lucy</Option>
                    <Option value="disabled" disabled>Disabled</Option>
                    <Option value="Yiminghe">yiminghe</Option>
                </Select>
                <Select defaultValue="lucy" style={{ width: 120 }} disabled>
                    <Option value="lucy">Lucy</Option>
                </Select>
            </div>

        );
    }
}
export default ExpenseSelector