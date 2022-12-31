const routes = {
    root: '/',
    login: '/login',
    branch: '/branches',
    manufactoringbase: '/branches/manufacturing_base',
    distributoragent: '/branches/distributor_agent',
    warrrantycenter: '/branches/warranty_center',
    accounts: '/accounts',
    model: '/models',
    product: '/products',
    warehouse: '/warehouses',
    factoryWarehousing: '/factories/warehousing',
    productComingDistribution: '/distributor/coming',
    productComingWarranty: '/warranties/coming',
    warrantyProducts: '/warranties/products',

    statisticalSold: '/statistical/sold',
    statisticalInStock: '/statistical/instock',
    statisticalWarranty: '/statistical/warranty',
    newPurchase: '/distributors/purchase',
    distributorWarranty: '/distributors/warranties',
    sendProductToWarranty: '/distributors/send_warranty',
    distributorWarrantyDone: '/distributors/warranties/products/done',
    adminStatisticalByModel: '/admin/statistical/model',
    

    no_match: '*',
};

export default routes;
