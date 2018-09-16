import React, { Component } from 'react';
import './App.css';
import {Link,
  Route,
  withRouter,
  Switch
} from 'react-router-dom';
import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
//import PollList from '../poll/PollList';
import ExpenseTypes from '../student/ExpenseTypes';
import StudentList from '../student/StudentList';
import NewPoll from '../poll/NewPoll';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import PrivateRoute from '../common/PrivateRoute';
import ExpenseSelector from '../student/ExpenseSelector';
import { Layout, Menu, Breadcrumb, Icon, notification } from 'antd';
import SideBar from '../common/SideBar';
import SumeryExpense from '../student/SumeryExpense';
import AircraftTypes from '../student/AircraftTypes';
import Aircrafts from '../student/Aircrafts';
import Discount from '../student/Discounts';
import FlyingList from '../student/FlyingList';
const { SubMenu } = Menu;
const { Header, Content, Footer, Sider } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
      }).catch(error => {
        this.setState({
          isLoading: false
        });
      });
  }

  componentWillMount() {
    this.loadCurrentUser();
  }

  handleLogout(redirectTo = "/", notificationType = "success", description = "You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);

    notification[notificationType]({
      message: 'AAA Student IMS',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'AAA Student IMS',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />
    }
    return (
      <Layout className="app-container">



        <div>

          <Layout>
            <AppHeader isAuthenticated={this.state.isAuthenticated}
              currentUser={this.state.currentUser}
              onLogout={this.handleLogout} />
            <Content style={{ padding: '0 50px' }}>
              <Breadcrumb style={{ margin: '16px 0' }}>
                <Breadcrumb.Item>Home</Breadcrumb.Item>
                <Breadcrumb.Item>List</Breadcrumb.Item>
                <Breadcrumb.Item>App</Breadcrumb.Item>
              </Breadcrumb>
              <Layout style={{ padding: '24px 0', background: '#fff' }}>

                <SideBar isAuthenticated={this.state.isAuthenticated} />
                <Content className="app-content">
                  <div className="container">
                    <Switch>

                      <Route path="/login"
                        render={(props) => <Login onLogin={this.handleLogin} {...props} />}></Route>
                      <Route path="/signup" component={Signup}></Route>
                      <Route path="/expenseTypes" component={ExpenseTypes}></Route>
                      <Route path="/aircraftTypes" component={AircraftTypes}></Route>
                      <Route path="/aircrafts" component={Aircrafts}></Route>
                      <Route path="/discount" component={Discount}></Route>
                      <Route path="/flyingList" component={FlyingList}></Route>



                      <Route path="/summery/:id" component={SumeryExpense}/>

                      <PrivateRoute authenticated={this.state.isAuthenticated} path="/" component={StudentList} handleLogout={this.handleLogout}></PrivateRoute>


                      <Route component={NotFound}></Route>
                    </Switch>
                  </div>
                </Content>
              </Layout>
            </Content>
            <Footer style={{ textAlign: 'center' }}>
            Asian Academy of Aeronautics ©2018 
    </Footer>
          </Layout>
        </div>




      </Layout>
    );
  }
}

export default withRouter(App);
