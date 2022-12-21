
import { Navigate} from "react-router-dom";

function PrivateRoute({children, user, redirectPath = '/login'}) {
    if (!user) {
        console.log("hello")
        return <Navigate to={redirectPath} replace />
    }

    return children;
}

export default PrivateRoute;