import classNames from "classnames/bind";
import FormAddBranch from "../../components/FormAddBranch";
import style from './ManufacturingBase.module.scss';

const cx = classNames.bind(style);

function ManufacturingBase() {
    return (<div className={cx('wrapper')}>
        ManufactoringBase
        <FormAddBranch />
    </div>);
}

export default ManufacturingBase;