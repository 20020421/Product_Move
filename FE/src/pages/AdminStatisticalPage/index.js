import { Card } from "antd";
import { useEffect, useState } from "react";
import { get } from "../../utils/request";

function AdminStatisticalPage() {

    const [statistical, setStatistical] = useState([]);

    useEffect(() => {
        get('/api/v1/products/statistical/model')
            .then(response => {
                setStatistical(Object.entries(response));
            })
    }, [])

    console.log(statistical)


    return ( <div style={{display: 'flex', gap: '20px', flexWrap : 'wrap'}}>
        {
            statistical && statistical.map((model, index) => (
                <Card style={{ width: '400px' }} title={`${model[0]}`} bordered={false}>
                        <div style={{ fontSize: '5rem', fontWeight: '700' }}>
                            {model[1]}
                        </div>
                    </Card>
            ))
        }
    </div> );
}

export default AdminStatisticalPage;