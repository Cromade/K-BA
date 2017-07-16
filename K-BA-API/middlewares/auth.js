'use strict';

const AuthMiddleware = {};

AuthMiddleware.getToken = function() {
    return (req, res, next) => {
        const auth = req.headers["authorization"];
        if(auth) {
            const splitAuth = auth.split(" ");
            if(splitAuth.length > 1 && splitAuth[0].toLowerCase() == "bearer") {
                req.token = splitAuth[1];
                return next();
            } 
        }
            res.status(401).end();
    }
}



module.exports = AuthMiddleware;