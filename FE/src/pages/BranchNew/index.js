import classNames from "classnames/bind";
import FormAddBranch from "../../components/FormAddBranch";
import style from "./BranchNew.module.scss";

const cx = classNames.bind(style);

function BranchNew() {
    return ( 
        <div className={cx('wrapper')}>
            <FormAddBranch />
        </div>
     );
}

export default BranchNew;