import Header from './Header';
import classNames from 'classnames/bind';
import styles from './DefaultLayout.module.scss';
import Footer from './Footer';
import Sidebar from '../../Sidebar';
import { useState } from 'react';
// import { publicRoutes } from '../../../routes';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ScrollToTop from '../../ScrollToTop';
import { Toaster } from 'react-hot-toast';

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
                    <Header toggleSidebar={handleToggleSidebar} />
                </div>
                <div className={cx('main-content')}>
                    {children}

                </div>


            </div>
            <Toaster 
            position="top-center"
            reverseOrder={false}
            gutter={8}
            containerClassName=""
            containerStyle={{}}
            toastOptions={{
              // Define default options
              className: '',
              duration: 5000,
              style: {
                background: '#363636',
                color: '#fff',
              },
          
              // Default options for specific types
              success: {
                duration: 3000,
                theme: {
                  primary: 'green',
                  secondary: 'black',
                },
              },
            }}  />
        </div>
    );
}

export default DefaultLayout;
