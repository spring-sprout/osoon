const LoginForm = () => (
    <form className="form-inline" action="/signin/facebook" method="post">
        <button className="btn btn-outline-success" type="Submit">로그인</button>
    </form>
);

const LoggedIn = (props) => (
    <li className="nav-item dropdown">
        <a className="nav-link dropdown-toggle" classId="userMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            {props.name}
        </a>
        <div className="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
            <a className="dropdown-item" href="#">프로필</a>
            <a className="dropdown-item" href="#">설정</a>
            <a className="dropdown-item" onClick={props.logout} href="#">로그아웃</a>
        </div>
    </li>
);

class UserMenu extends React.Component {

    constructor(...args) {
        super(...args);
        this.state = {name: null};
    }

    componentDidMount() {
        fetch('/api/session', {credentials: 'same-origin'})
            .then(res => res.json())
            .then(session => this.setState({name: session.name}));
    }

    logout() {
        console.log("logout");
        fetch('/api/session', {method: 'delete', credentials: 'same-origin'})
            .then(res => this.setState({name: null}));
    }

    render() {
        const profile = this.state.name ?
            <LoggedIn name={this.state.name} logout={() => this.logout()}/> :
            <LoginForm />;
        return (
            <div>
                {profile}
            </div>
        )
    }
}

ReactDOM.render(<UserMenu />, document.getElementById('userContainer'));
