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
import WarehousePage from '../pages/WarehousePage';
import Warehousing from '../pages/WarehousingPage';
import WarehousingPage from '../pages/WarehousingPage';
import ProductPage from '../pages/ProductPage';
import ComingDistributor from '../pages/ComingDistributor';
import SoldStatistical from '../pages/StatisticalPage/SoldStatistical';
import Purchase from '../pages/Purchase';
import DisWarranty from '../pages/DisWarranty';
import SendProductToWarranty from '../pages/SendProductToWarranty';
import ComingWarranty from '../pages/ComingWarranty';
import ProductManagerWarranty from '../pages/ProductManagerWarranty';
import DistributorWarrantyDone from '../pages/DistributorManagerWarranty';
import AdminStatisticalPage from '../pages/AdminStatisticalPage';


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
        title: 'Warehouses',
        path: routes.warehouse,
        component: WarehousePage
    },
    {
        title: 'Warehousing',
        path: routes.factoryWarehousing,
        component: WarehousingPage
    },
    {
        title: 'Products',
        path: routes.product,
        component: ProductPage
    },
    {
        title: 'Coming',
        path: routes.productComingDistribution,
        component: ComingDistributor
    },
    {
        title: 'Coming',
        path: routes.productComingWarranty,
        component: ComingWarranty
    },
    {
        title: 'Statistical Sold',
        path: routes.statisticalSold,
        component: SoldStatistical
    },
    {
        title: 'Purchase',
        path: routes.newPurchase,
        component: Purchase
    },
    {
        title: 'DisWarranty',
        path: routes.distributorWarranty,
        component: DisWarranty
    },
    {
        title: "",
        path: routes.sendProductToWarranty,
        component: SendProductToWarranty
    }, 

    {
        title: "Products",
        path: routes.warrantyProducts,
        component: ProductManagerWarranty
    }, 
    {
        title: "Products",
        path: routes.distributorWarrantyDone,
        component: DistributorWarrantyDone
    }, 
    {
        title: "Statistical",
        path: routes.adminStatisticalByModel,
        component: AdminStatisticalPage
    }, 
    
    {
        title: 'Not Found',
        path:routes.no_match,
        component: NoRouteMatch
    },
    


];

export { publicRoutes, privateRoutes };
