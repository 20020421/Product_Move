import classNames from "classnames/bind";
import style from "./FormNewAccount.scss";
import { Select } from "antd";
import { useEffect, useState } from "react";
import { get } from "../../utils/request";


const cx = classNames.bind(style);

function FormNewAccount({state, setState}) {

    // console.log(state)


    const handlOnChange = (e) => {

        let value;
        if (e.target.type === 'file') {
            value = e.target.files[0];
        } else {
            value = e.target.value;
        }

        setState({
            ...state,
            [e.target.id]: value
        })
        
    }


    const [optionBranches, setOptionBranches] = useState([]);
    useEffect(() => {
        get("/api/v1/branches/names")
            .then(response => setOptionBranches(response))
            .then(error => console.log(error));
    }, [])

    return (<form className={cx('form')}>
        <div className={cx('form-group')}>
            <label htmlFor="username" className={cx('form-label')}>
                Username
            </label>
            <input onChange={handlOnChange} value={state.username} type='text' name="username" className={cx('form-control','field')} id="username" />
        </div>
        <div className={cx('form-group')}>
            <label  htmlFor="password" className={cx('form-label')}>
                Password
            </label>
            <input onChange={handlOnChange} value={state.password} type='password' name="password" className={cx('form-control','field')} id="password" />
        </div>
        <div className={cx('form-group')}>
            <label htmlFor="avatarFile" className={cx('form-label')}>
                Avatar
            </label>
            <input  onChange={handlOnChange} type='file' name="avatarFile" className={cx('form-control','field')} id="avatarFile" />
        </div>
        <div className={cx('form-group')}>
            <label htmlFor="branch" className={cx('form-label')}>Choose Branch</label>
            <div className={cx('form-control','field')}>
            <Select
                dropdownStyle={{
                    zIndex: '999999'
                }}
                showSearch
                style={{ width: 200 }}
                placeholder="Select a branch"
                optionFilterProp="children"
                onSelect={(value, event) => {
                    setState({
                        ...state,
                        branchName: value
                    })
                }}
                filterOption={(input, option) =>
                    option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                }
            >
                {
                    optionBranches.map((optionBranch, index) => (
                        <Select.Option key={index} value={optionBranch}>{optionBranch}</Select.Option>
                    ))
                }
            </Select>

            </div>
        </div>

    </form>);
}

export default FormNewAccount;