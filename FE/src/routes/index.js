import HomePage from '../pages/HomePage';
import LoginPage from '../pages/LoginPage';
import routes from '../configs/routes';
import DefaultLayout from '../components/layouts/DefaultLayout';
import React from 'react';
import ManufacturingBase from '../pages/ManufacturingBase';
import NoRouteMatch from '../pages/NoRouteMatch';


const publicRoutes = [
    {
        title: 'Login',
        path: routes.login,
        component: LoginPage,
        layout: React.Fragment
    },
    // {
    //     title: 'Home',
    //     path: routes.root,
    //     component: HomePage,
    //     layout: DefaultLayout,
    // },
    {
        title: 'Not Found',
        path:routes.no_match,
        component: NoRouteMatch,
        layout:DefaultLayout
    }
    
];

const privateRoutes = [
    {
        title: 'Home',
        path: routes.root,
        component: HomePage,
        layout: DefaultLayout,
    },
    {
        title: 'Manufatoring Base',
        path: routes.manufactoringbase,
        component: ManufacturingBase,
        layout:DefaultLayout,
    }

];

export { publicRoutes, privateRoutes };
