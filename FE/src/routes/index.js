import HomePage from '../pages/HomePage';
import LoginPage from '../pages/LoginPage';
import routes from '../configs/routes';
import DefaultLayout from '../components/layouts/DefaultLayout';
import React from 'react';
import ManufacturingBase from '../pages/ManufacturingBase';
import NoRouteMatch from '../pages/NoRouteMatch';
import BranchNew from '../pages/BranchNew';
import UserPage from '../pages/UserPage';
import DistributorAgent from '../pages/DistributorAgent';
import WarrantyCenter from '../pages/WarrantyCenter';
import ModelPage from '../pages/ModelPage';


const publicRoutes = [
    {
        title: 'Login',
        path: routes.login,
        component: LoginPage,
        
    },
    // {
    //     title: 'Home',
    //     path: routes.root,
    //     component: HomePage,
    //     layout: DefaultLayout,
    // },
    
    
];

const privateRoutes = [
    {
        title: 'Home',
        path: routes.root,
        component: HomePage,
    },
    {
        title: 'Manufatoring Base',
        path: routes.manufactoringbase,
        component: ManufacturingBase,
    },
    {
        title: 'Distributor Agent',
        path: routes.distributoragent,
        component: DistributorAgent
    },
    {
        title: 'Warranty Center',
        path: routes.warrrantycenter,
        component: WarrantyCenter
    },
    {
        title: 'Branches',
        path: routes.branch,
        component: BranchNew
    },
    {
        title: 'Users',
        path: routes.accounts,
        component: UserPage

    },
    {
        title: 'Models',
        path: routes.model,
        component: ModelPage

    },
    {
        title: 'Not Found',
        path:routes.no_match,
        component: NoRouteMatch
    },

];

export { publicRoutes, privateRoutes };
