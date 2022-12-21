import Header from './Header';
import classNames from 'classnames/bind';
import styles from './DefaultLayout.module.scss';
import Footer from './Footer';
import Sidebar from '../../Sidebar';
import { useState } from 'react';

const cx = classNames.bind(styles);
function DefaultLayout({ children }) {

    const [hideSidebar, setHideSidebar] = useState(false);

    const handleToggleSidebar = () => {
        setHideSidebar(!hideSidebar);
    }

    return (
        <div className={cx('wrapper')}>
            <div className={cx('sidebar', [hideSidebar ? 'hide' : ''])}>
                <Sidebar />
            </div>
            <div className={cx('main')}>
                <div className={cx('header')}>
                    <Header toggleSidebar = {handleToggleSidebar} />
                </div>
                <div className={cx('main-content')}>
                    {children}
                </div>


            </div>

        </div>
    );
}

export default DefaultLayout;
