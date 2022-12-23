import axios from "axios";
import classNames from "classnames/bind";
import { useContext, useState } from "react";
import { AuthContext } from "../../context/UserContext";
import { addBranch } from "../../utils/request";
import style from './FormAddBranch.module.scss';

const cx = classNames.bind(style);


const branchTypes = [
    {
        type: 'Distributor Agent',
        value: "Distributor Agent"
    },
    {
        type: 'Factory',
        value: 'Factory'
    },
    {
        type: 'Warranty Center',
        value: 'Warranty Center'
    },

]

function FormAddBranch() {


    const [branch, setBranch] = useState({
        type: branchTypes[0].value,
        name: '',
        address: '',
        phone: '',
    })

    const [userState, dispatch] = useContext(AuthContext);

    const [errorMess, setErrorMess] = useState("");

    const branchIsValid = () => {
        return branch.type !== '' && branch.address !== '' && branch.name !== '' && branch.phone !== '';
    }

    const handleOnCreate = async (event) => {
        event.preventDefault()
        console.log(branchIsValid())

        if (branchIsValid) {
            const data = JSON.stringify(branch);
            const config = {
                method: 'post',
                url: 'http://localhost:8080/api/v1/branches',
                headers: {
                    'Authorization': "Bearer " + userState.accessToken,
                    'Content-Type': 'application/json'
                },
                data: data
            };
            axios(config)
                .then(response => {
                    if (response.status === 200) {
                        resetField();
                        alert("Add success fully");
                    }
                })
                .catch(error => {
                    setErrorMess("New creation failed. Try again.")
                });
        }
    }

    const resetField = () => {
        setBranch({
            type: branchTypes[0].value,
            name: '',
            address: '',
            phone: '',
        })
    }






    return (<div className={cx('wrapper')}>
        <form spellCheck={false}>
            <div className={cx("form-group")}>
                <label htmlFor='branch_type' className={cx("form-label")}>
                    Branch Type
                </label>
                <select name="branch_type" id="branch_type" value={branch.type} onChange={(event) => {
                    setBranch({
                        ...branch,
                        type: event.target.value
                    })
                    setErrorMess("")
                }}>
                    {
                        branchTypes.map((type, index) => (
                            <option key={index} value={type.value}>
                                {type.type}
                            </option>
                        ))
                    }
                </select>
            </div>
            <div className={cx('form-group')}>
                <label htmlFor="branchname" className={cx('form-label')}>
                    Branch name
                </label>
                <input type='text' value={branch.name} className={cx('form-control')} name="branchname" id="branchname" onChange={(event) => {
                    setErrorMess("")
                    setBranch({
                    ...branch,
                    name: event.target.value
                })}} />
            </div>
            <div className={cx('form-group')}>
                <label htmlFor="branchaddress" className={cx('form-label')}>
                    Branch address
                </label>
                <input type='text' value={branch.address} className={cx('form-control')} name="branchaddress" id="branchaddress" onChange={(event) => {
                    setErrorMess("")
                    setBranch({
                    ...branch,
                    address: event.target.value
                })}} />
            </div>
            <div className={cx('form-group')}>
                <label htmlFor="phone" className={cx('form-label')}>
                    Phone
                </label>
                <input type='text' value={branch.phone} className={cx('form-control')} name="branchphone" id="branchphone" onChange={(event) => {
                    setErrorMess("")
                    setBranch({
                    ...branch,
                    phone: event.target.value
                })}} />
            </div>
            <div className={cx('error-message')}>
                {
                    errorMess !== "" && <span>
                        {errorMess}
                    </span>
                }
            </div>

            <button className={cx('btn-submit', [branchIsValid() ? '' : 'disable'])} type="submit" onClick={handleOnCreate}>Create</button>

        </form>

    </div>);
}

export default FormAddBranch;