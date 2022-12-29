import classNames from "classnames/bind";
import style from "./FormAddWarehouse.module.scss";

const cx = classNames.bind(style);

function FormAddWarehouse({state, setState}) {


    const handleOnChange = (event) => {
        setState({
            ...state,
            [event.target.name]: event.target.value
        })
    }
    return ( <form className={cx('form')}>
        <div className={cx('form-group')}>
            <label htmlFor="name" className={cx('form-label')}>
                Name warehouse
            </label>
            <input autoComplete="off" spellCheck="off" value={state.name} onChange={handleOnChange} type='text' name="name" className={cx('form-control', 'field')} id="name" />
        </div>
        <div className={cx('form-group')}>
            <label htmlFor="address" className={cx('form-label')}>
                Address
            </label>
            <input autoComplete="off" spellCheck="off" value={state.address} onChange={handleOnChange} type='text' name="address" className={cx('form-control', 'field')} id="address" />
        </div>
    </form> );
}

export default FormAddWarehouse;