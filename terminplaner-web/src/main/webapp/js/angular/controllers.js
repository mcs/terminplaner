"use strict";

terminplanerApp.controller("BodyController", ["$scope", "$location", "$http", function ($scope, $location, $http) {

    $scope.user = {
        username: "",
        authKey: "",
        isAuthorized: function () {
            return this.username && this.authKey;
        }
    };

    $scope.updateHttpAuthHeaders = function () {
        $http.defaults.headers.common["auth_key"] = $scope.user.authKey;
    };

    $scope.isCurrentPage = function (testpage) {
        return $location.path() == testpage;
    };

    $scope.alerts = [
        /*
         { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
         { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
         */
    ];

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };

    /**
     * Logout a user and update HTTP Auth Headers
     */
    $scope.logout = function () {
        $scope.user.username = "";
        $scope.user.authKey = "";
        $scope.updateHttpAuthHeaders();
        $scope.alerts.push({type: "success", msg: "Abmeldung erfolgreich."});
    };

}]);

terminplanerApp.controller("TerminNeuController", ["$scope", "Termine", function ($scope, Termine) {
    $scope.submit = function () {
        Termine.save($scope.termin, function (termin, httpResponse) {
                console.log("Termin = %o, HttpResponse = %o", termin, httpResponse);
                $scope.alerts.push({type: "success", msg: "Termin erfolgreich angelegt."});
                $scope.termin = new Termine();
            }, function (x, y, z) {
                console.error("x = %o - y = %o - z = %o", x, y, z);
            }
        );
    };

    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };
}]);

terminplanerApp.controller("UserLoginController", ["$scope", "$location", "Login", function ($scope, $location, Login) {
    /**
     * Login a user and update HTTP Auth Headers
     */
    $scope.submit = function () {
//            Login.save({username: $scope.username, password: $scope.password}, function (user) {
//                $scope.user.username = user.username;
//                $scope.user.authKey = user.authKey;
//                $scope.updateHttpAuthHeaders();
//                console.log("User erfolgreich angemeldet: %o", user);
//            }, function (response) {
//                console.error("Response = %o", response);
//            });
        var result = Login.send($scope.username, $scope.password);
        result.success(function (user) {
            $scope.user.username = user.username;
            $scope.user.authKey = user.authKey;
            $scope.updateHttpAuthHeaders();
            $location.path("/");
            $scope.alerts.push({type: "success", msg: "Willkommen, " + user.username});
        });
        result.error(function (data, statusCode, headers) {
            $scope.alerts.push({type: "danger", msg: "Anmeldung fehlgeschlagen."});
            console.error("data = %o, statusCode = %o", data, statusCode);
        });
    };

}]);