
import { useContext } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PrivateRoute from './components/PrivateRoute';
import ScrollToTop from './components/ScrollToTop';
import { AuthContext } from './context/UserContext';
import { privateRoutes, publicRoutes } from './routes';

function App() {
    const [userState, dispatch] = useContext(AuthContext);
    // console.log(userState.userInfo.role)
    
    return (
        <>
            <Router>
                <ScrollToTop>
                    <Routes>
                        {privateRoutes.map((route, index) => {
                            let Layout = route.layout;

                            const Page = route.component;

                            const Component  = <Layout><Page /></Layout>

                            return (
                                <Route
                                    key={index}
                                    path={route.path}
                                    element={
                                        <PrivateRoute user={userState.isLoggedIn}>
                                            {Component}
                                        </PrivateRoute>
                                    }
                                />
                            );
                        })}
                        {publicRoutes.map((route, index) => {
                            let Layout = route.layout;

                            const Page = route.component;

                            const Component  = <Layout><Page /></Layout>

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
                </ScrollToTop>
            </Router>
        </>
    );
}

export default App;
