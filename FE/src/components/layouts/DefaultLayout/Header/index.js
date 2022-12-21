import { faBars, faGear} from "@fortawesome/free-solid-svg-icons";
import {  faBell, faMessage } from "@fortawesome/free-regular-svg-icons";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classNames from "classnames/bind";
import { images } from "../../../../assets/images";
import style from './Header.module.scss';
import UserOptions from "./UserOptions";
import useComponentVisible from "../../../../hooks/useComponentVisible";
import { Link } from "react-router-dom";

const cx = classNames.bind(style);


function Header({toggleSidebar}) {


    const {ref, isComponentVisible, setIsComponentVisible} = useComponentVisible(false);

    return (
        <div className={cx("wrapper")}>
            <div className={cx('container-fluid','header')}>
                <div className={cx('header-toggle')} onClick={toggleSidebar}>
                    <FontAwesomeIcon icon={faBars} className={cx('icon-toggle', 'icon')} />
                </div>
                <div className={cx('header-setting')}>
                    <FontAwesomeIcon className={cx('header-setting-icon', 'icon')} icon={faGear} />
                    <span>Setting</span>
                </div>
                <ul className={cx('header-nav')}>
                    <li className={cx('nav-item')}>
                        <div className={cx('nav-link')}>
                            <FontAwesomeIcon icon={faBell} className={cx('nav-icon', 'icon')} />
                        </div>
                    </li>
                    <li className={cx('nav-item')}>
                        <div className={cx('nav-link')}>
                            <FontAwesomeIcon icon={faMessage} className={cx('nav-icon', 'icon')} />
                        </div>
                    </li>
                </ul>
                <div className={cx('user-avatar')} ref={ref} onClick={() => setIsComponentVisible(!isComponentVisible)}>
                    <div className={cx('avatar')}>
                        <img src={images.testAvatar} alt='avatar' />
                    </div>
                    {
                        isComponentVisible && <UserOptions />
                    }
                    
                </div>
            </div>
            <div className={cx('header-divider')}></div>
            <div className={cx('container-fluid', 'header-breadcrumb')}>
                <nav className={cx('breadcrumb')}>
                    <ol className={cx('breadcrumb-list')}>
                        <li className={cx('breadcrumb-item')}>
                            <Link to='/'>
                                Home
                            </Link>
                        </li>
                        <li className={cx('breadcrumb-item')}>
                            <Link to='/'>
                                Home
                            </Link>
                        </li>
                        <li className={cx('breadcrumb-item', 'active')}>
                            <Link to='/'>
                                Home
                            </Link>
                        </li>
                    </ol>
                </nav>
            </div>
        </div>);
}

export default Header;