
import { Fragment, useContext, useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DefaultLayout from './components/layouts/DefaultLayout';
import ScrollToTop from './components/ScrollToTop';
// import DefaultLayout from './components/layouts/DefaultLayout';
import PrivateRoute from './components/PrivateRoute';
// import ScrollToTop from './components/ScrollToTop';
// import Sidebar from './components/Sidebar';
import { AuthContext } from './context/UserContext';
import { privateRoutes, publicRoutes } from './routes';
// import { privateRoutes, publicRoutes } from './routes';

function App() {
    const [userState, dispatch] = useContext(AuthContext);
    // console.log(userState.userInfo.role)


    
    return (
 
        <Router>
            <ScrollToTop>
                {
                    userState.isLoggedIn ? (<DefaultLayout>
                        <Routes>
                    {privateRoutes.map((route, index) => {

                            const Page = route.component;

                            return (
                                <Route
                                    key={index}
                                    path={route.path}
                                    element={
                                        <PrivateRoute user={userState.isLoggedIn}>
                                            <Page />
                                        </PrivateRoute>
                                    }
                                />
                            );
                        })}
                    {publicRoutes.map((route, index) => {

                            const Page = route.component;

                            const Component  = <Page />

                            return (
                                <Route
                                    key={index}
                                    path={route.path}
                                    element={
                                        Component
                                    }
                                />
                            );
                        })}
                    </Routes>
                    </DefaultLayout>) : (<>
                        <Routes>
                    {privateRoutes.map((route, index) => {

                            const Page = route.component;

                            return (
                                <Route
                                    key={index}
                                    path={route.path}
                                    element={
                                        <PrivateRoute user={userState.isLoggedIn}>
                                            <Page />
                                        </PrivateRoute>
                                    }
                                />
                            );
                        })}
                    {publicRoutes.map((route, index) => {

                            const Page = route.component;

                            const Component  = <Page />

                            return (
                                <Route
                                    key={index}
                                    path={route.path}
                                    element={
                                        Component
                                    }
                                />
                            );
                        })}
                    </Routes>
                    </>)
                }
                
                    
                
            </ScrollToTop>
        </Router>
        
    );
}

export default App;
