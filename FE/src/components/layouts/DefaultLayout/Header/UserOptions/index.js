import { faBell, faCommentDots, faCreditCard, faEnvelopeOpen, faFile, faGear, faListCheck, faRightFromBracket, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classNames from "classnames/bind";
import { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../../../../context/UserContext";
import { userLogout } from "../../../../../store/actions/userAction";
import style from './UserOptions.module.scss';

const cx = classNames.bind(style);

const userOptions = [
    {
        group: 'Account',
        children: [
            {
                icon: faBell,
                text: 'Updates',
                to: '/updates',
                range: 42,
            },
            {
                icon: faEnvelopeOpen,
                text: 'Messages',
                to: '/messages',
                range: 42,
            },
            {
                icon: faListCheck,
                text: 'Tasks',
                to: '/tasks',
                range: 42,
            },{
                icon: faCommentDots,
                text: 'Comments',
                to: '/comments',
                range: 42,
            },
        ]
    },
    {
        group: 'Settings',
        children: [
            {
                icon: faUser,
                text: 'Profile',
                to: '/profile',
            },
            {
                icon: faGear,
                text: 'Settings',
                to: '/settings'
            },
            {
                icon: faCreditCard,
                text: 'Payments',
                to: '/payments',
                range: 42,
            },{
                icon: faFile,
                text: 'Projects',
                to: '/projects',
                range: 42,
            },
        ]

    }
]

function UserOptions() {

    const [useState, dispatch] = useContext(AuthContext);

    const handleLogout = () => {
        console.log("logout")
        dispatch(userLogout());

    }


    return ( <div className={cx('wrapper')}>
        <div className={cx('options')}>
            {
                userOptions.map((group, index) => (
                    <div key={index} className={cx('options-group')}>
                        <div className={cx('group-header')}>
                            <span>{group.group}</span>
                        </div>
                        <div className={cx('option-items')}>
                        {
                            group.children.map((option, index) => (
                                <Link to={option.to} key={index} className={cx('option-item')}>
                                    <FontAwesomeIcon icon={option.icon} />
                                    <span>{option.text}</span>
                                    {
                                        option.range && (
                                            <div className={cx('option-range')}>
                                                {option.range}
                                            </div>
                                        )
                                    }
                                </Link>
                            ))
                        }
                        </div>
                    </div>
                ))
            }
            <div className={cx('logout')} onClick={handleLogout}>
                <FontAwesomeIcon icon={faRightFromBracket} />
                <span>Logout</span> 
            </div>
        </div>
    </div> );
}

export default (UserOptions);