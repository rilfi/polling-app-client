import React from 'react';
import {Link} from 'react-router-dom';
import { Layout, Menu, Icon,  } from 'antd';

const { SubMenu } = Menu;
const {  Sider } = Layout;

const SideBar=(props)=>{

    console.log(props)

    if(props.isAuthenticated){
        return(

        <div>
            <Sider width={250} style={{ background: '#fff' }}>
                  <Menu
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    style={{ height: '100%' }}
                  >

                    <SubMenu key="sub1" title={<span><Icon type="user" />Student Management</span>}>

                      <Menu.Item key="/"> Student List
                        <Link to="/">
                        </Link>
                      </Menu.Item>
                      <Menu.Item key="/expenseTypes">Expense Types
                      <Link to="/expenseTypes"></Link>
                      </Menu.Item>
                      <Menu.Item key="/discount">Discounts
                      <Link to="/discount"></Link>
                      </Menu.Item>
                      <Menu.Item key="/flyingList">Flying List
                      <Link to="/flyingList"></Link>
                      </Menu.Item>

                    </SubMenu>
                    
                  </Menu>
                </Sider>
        </div>
        );
        

        }
        else{
            return (<div></div>)
        }

    
}
export default SideBar;