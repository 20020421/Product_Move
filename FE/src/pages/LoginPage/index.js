import { faSquare, faSquareCheck } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classNames from "classnames/bind"; 
import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import request, { login } from "../../utils/request";
import style from './LoginPage.module.scss';
import axios from "axios";
import { AuthContext } from "../../context/UserContext";
import { userLogginSuccess } from "../../store/actions/userAction";

const cx = classNames.bind(style);


function LoginPage() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [saveInfo, setSaveInfo] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [isDisable, setIsDisable] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        if (username !== '' && password !== '') {
            setIsDisable(false);
        } else {
            setIsDisable(true);
        }
    },[username, password])

    const FIELDNAME = {
        username: 'username',
        password: 'password',
    }

    const handleOnChangeField = (e) => {
        const fieldName = e.target.name;
        setErrorMsg('')
        if (fieldName === FIELDNAME.username) {
            setUsername(e.target.value);
        } else if (fieldName === FIELDNAME.password) {
            setPassword(e.target.value);
        }
    }

    const [userState, dispatch] = useContext(AuthContext);

    const handleOnSubmit = async (e) => {
        e.preventDefault();
        if (username !== '' && password !== '') {
            
            // const response = await login(username, password);
            // console.log(response)
            // if (response.status === 200) {
            //     dispatch(userLogginSuccess(response.data))
            //     return navigate("/");
            // } else if (response.status === 401) {
            //     setErrorMsg(response.data)
            //     console.log(response.data)
            // }
            login(username, password)
                .then(response => {
                    if (response.status === 200) {
                        dispatch(userLogginSuccess(response.data))
                        return navigate("/");
                    }
                })
                .catch(error => {
                    setErrorMsg(error.response.data)
                })
            
        } else {
            setErrorMsg("Username and password is required!!!")
        }
    }


    return <div className={cx('wrapper')}>
    <div className={cx('container')}>
        <h3>Login</h3>
        <form onSubmit={handleOnSubmit}>
            <div className={cx('form-control')}>
                <input
                    placeholder=" "
                    type="text"
                    onChange={handleOnChangeField}
                    id='user-name'
                    name={FIELDNAME.username}
                    value={username}
                />
                <label htmlFor='user-name'>Username</label>
            </div>
            <div className={cx('form-control')}>
                <input
                    placeholder=" "
                    type="password"
                    onChange={handleOnChangeField}
                    id='password'
                    name={FIELDNAME.password}
                    value={password}
                />
                <label htmlFor='password'>Password</label>
            </div>
            <span className={cx('err-message')}>{errorMsg}</span>
            <div className={cx('help')}>
                <div className={cx('save')}>
                    <FontAwesomeIcon
                        onClick={() => setSaveInfo(!saveInfo)}
                        className={cx('icon')}
                        icon={saveInfo ? faSquareCheck : faSquare}
                    />
                    <p>Remember me</p>
                </div>
                <div className={cx('forgot_password')}>
                    <Link to="/forgot_password">Forgot Password ?</Link>
                </div>
            </div>
            <div className={cx('submit')}>
                <button disabled={isDisable} className={cx('button')}>Login</button>
            </div>
        </form>
    </div>
</div>
;
}

export default LoginPage;