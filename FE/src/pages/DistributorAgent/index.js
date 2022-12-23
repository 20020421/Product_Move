import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { optionNumber } from "../../utils/optionValue";
import { get } from "../../utils/request";
import style from './DistributorAgent.module.scss';

const cx = classNames.bind(style);

function DistributorAgent() {

    const DISTRIBUTOR_AGENT = "Distributor Agent"

    const [distributors, setDistributor] = useState([]);

    useEffect(() => {

        get(`/api/v1/branches/${DISTRIBUTOR_AGENT}`)
            .then(response => setDistributor(response))
            .catch(error => console.log(error));

    }, [])

    const [filterState, setFilterState] = useState({
        quantity: optionNumber[0].value,
        name: '',
        address: '',
        phone: '',
    })



    console.log(distributors)
    return (<div className={cx('wrapper')}>
    <h1 className={cx('distributor-header')}>
        Distributors
    </h1>
    <div className={cx('table')}>
    <table className={cx('distributors')}>
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
                    Distributor Agent
                </th>
                <th>
                    Address
                </th>
                <th>
                    Phone
                </th>
                <th>
                    Number of accounts
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
                   <input value={filterState.name} onChange={(e) => setFilterState({
                    ...filterState,
                    name: e.target.value
                   })} autoComplete='off' type="text" name="name" spellCheck={false} className={cx('filter-field')} /> 
                </th>
                <th>
                   <input value={filterState.address} onChange={(e) => setFilterState({
                    ...filterState,
                    address: e.target.value
                   })} type="text" name="address" spellCheck={false}  className={cx('filter-field')} /> 
                </th>
                <th>
                   <input value={filterState.phone} onChange={(e) => setFilterState({
                    ...filterState,
                    phone: e.target.value
                   })} type="text" name="phone" spellCheck={false} className={cx('filter-field')} /> 
                </th>
                <th>
                &nbsp;
                </th>
            </tr>
        </thead>
        <tbody>
            {
                distributors.map((factorie, index) => (
                    <tr key={index}>
                        <td>{index}</td>
                        <td>{factorie.name}</td>
                        <td>{factorie.address}</td>
                        <td>{factorie.phone}</td>
                        <td>{factorie.usersName.length}</td>
                    </tr>
                ))
            }
        </tbody>

    </table>
    </div>
</div>);
}

export default DistributorAgent;