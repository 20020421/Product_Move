import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { optionNumber } from "../../utils/optionValue";
import { get } from "../../utils/request";
import style from "./UserPage.module.scss";

const cx = classNames.bind(style);


function UserPage() {

    const [accounts, setAccounts] = useState([]);

    useEffect(() => {
        get('/api/v1/users')
            .then(response => setAccounts(response))
            .catch(error => console.log(error));
    },[])
    // const [quantity, setQuantity] = useState(optionNumber[0].value);
    const [filterState, setFilterState] = useState({
        quantity: optionNumber[0].value,
        username: '',
        role: '',
        branchName: '',
        createdAt: '',
    })

    return ( <div className={cx('wrapper')}>
        <h1 className={cx('account-header')}>
            Accounts
        </h1>
        <div className={cx('table')}>
        <table className={cx('accounts')}>
            <thead>
                <tr className={cx('header-tb')}>
                    <th className={cx('c0')}>
                        <select value={filterState.quantity} onChange={(e) => {
                            setFilterState({
                                ...filterState,
                                quantity: e.target.value
                            });
                        } }>
                            {
                                optionNumber.map((option, index) =>(
                                    <option key={index} value={option.value}>
                                        {option.value}
                                    </option>
                                ))
                            }
                        </select>
                    </th>
                    <th>
                        Username
                    </th>
                    <th>
                        Role
                    </th>
                    <th>
                        Branch name
                    </th>
                    <th>
                        Created at
                    </th>
                    <th>
                        Actions
                    </th>
                </tr>
                <tr className={cx('filter')}>
                    <th>
                        &nbsp;
                    </th>
                    <th>
                       <input value={filterState.username} onChange={(e) => setFilterState({
                        ...filterState,
                        username: e.target.value
                       })} autoComplete='off' type="text" name="username" spellCheck={false} className={cx('filter-field')} /> 
                    </th>
                    <th>
                       <input value={filterState.role} onChange={(e) => setFilterState({
                        ...filterState,
                        role: e.target.value
                       })} type="text" name="role" spellCheck={false}  className={cx('filter-field')} /> 
                    </th>
                    <th>
                       <input value={filterState.branchName} onChange={(e) => setFilterState({
                        ...filterState,
                        branchName: e.target.value
                       })} type="text" name="branchName" spellCheck={false} className={cx('filter-field')} /> 
                    </th>
                    <th>
                       <input value={filterState.createdAt} onChange={(e) => setFilterState({
                        ...filterState,
                        createdAt: e.target.value
                       })} type="date" name="Created at" spellCheck={false} className={cx('filter-field')} /> 
                    </th>
                </tr>
            </thead>
            <tbody>
                {
                    accounts.map((account, index) => (
                        <tr key={index}>
                            <td>{index}</td>
                            <td>{account.username}</td>
                            <td>{account.roleName}</td>
                            <td>{account.branchName}</td>
                            <td>{account.createdAt}</td>
                        </tr>
                    ))
                }
            </tbody>

        </table>
        </div>
    </div> );
}

export default UserPage;