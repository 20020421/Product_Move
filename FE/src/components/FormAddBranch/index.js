import classNames from "classnames/bind";
import { useState } from "react";
import style from './FormAddBranch.module.scss';

const cx = classNames.bind(style);


const branchTypes = [
    {
        type: 'Distributor Agent',
        value: "DISTRIBUTOR_AGENT"
    },
    {
        type: 'Factory',
        value: 'FACTORY'
    },
    {
        type: 'Warranty Center',
        value: 'WARRANTY_CENTER'
    },

]

function FormAddBranch() {


    const [branch, setBranch] = useState({
        type: branchTypes[0].value,
        name: '',
        address: ''
    })

    const branchIsValid = () => {
        return branch.type !== '' && branch.address !== '' && branch.name !== '';
    }

    const handleOnCreate = (event) => {
        event.preventDefault()
        console.log(branchIsValid())
        if (branchIsValid()) {
            console.log(branch)
        }
    }

    


    return (<div className={cx('wrapper')}>
        <form>
            <div className={cx("form-group")}>
                <label htmlFor='branch_type' className={cx("form-label")}>
                    Branch Type
                </label>
                <select name="branch_type" id="branch_type" value={branch.type} onChange={(event) => {
                    setBranch({
                        ...branch,
                        type: event.target.value
                    })
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
                <input type='text' value={branch.name} className={cx('form-control')} name="branchname" id="branchname" onChange={(event) => setBranch({
                    ...branch,
                    name: event.target.value
                })}/>
            </div>
            <div className={cx('form-group')}>
                <label htmlFor="branchaddress" className={cx('form-label')}>
                    Branch address
                </label>
                <input type='text' value={branch.address} className={cx('form-control')} name="branchaddress" id="branchaddress" onChange={(event) => setBranch({
                    ...branch,
                    address: event.target.value
                })}/>
            </div>

            <button className={cx('btn-submit',[branchIsValid() ? '' : 'disable'])} type="submit" onClick={handleOnCreate}>Create</button>

        </form>

    </div>  );
}

export default FormAddBranch;