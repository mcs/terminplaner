"use strict";

terminplanerApp.controller("BodyController", ["$scope", "$location", "$http", "Logout",
    function ($scope, $location, $http, Logout) {

        $scope.user = {
            username: "",
            authKey: "",
            isAuthorized: function () {
                return this.authKey && this.email;
            }
        };

        $scope.updateHttpAuthHeaders = function () {
            $http.defaults.headers.common["auth_key"] = $scope.user.authKey;
        };

        $scope.isCurrentPage = function (testpage) {
            return $location.path().match(testpage);
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
            Logout.save($scope.user);
            $scope.user.username = "";
            $scope.user.authKey = "";
            $scope.updateHttpAuthHeaders();
            $scope.alerts.push({type: "success", msg: "Abmeldung erfolgreich."});
        };

    }]);

terminplanerApp.controller("TerminNeuController", ["$scope", "Termine", function ($scope, Termine) {
    $scope.$watch("termin.emailsRaw", function (rawEmails) {
        if (typeof rawEmails === "string" && rawEmails) {
            $scope.termin.emails = rawEmails.split(/[,;\s\r\n]+/);
        }

    });

    $scope.submit = function () {
        Termine.save($scope.termin, function (termin, httpResponse) {
                console.log("Termin = %o, HttpResponse = %o", termin, httpResponse);
                $scope.alerts.push({type: "success", msg: "Termin erfolgreich angelegt."});
                $scope.termin = new Termine();
            }, function (response) {
                $scope.alerts.push({type: "danger", msg: "Der Termin konnte nicht angelegt werden: " + response.statusText});
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

terminplanerApp.controller("TermineController", ["$scope", "Termine", "Invitations", function ($scope, Termine, Invitations) {
    $scope.termine = Termine.query(function () {
        (function (termine) {
            var termin,
                i,
                invitations;
            console.log("Starte Laden der Einladungen...");
            for (i = 0; i < termine.length; i += 1) {
                termin = termine[i];
                console.log("Lade Einladung fuer Termin %o", termin);
                invitations = Invitations.query({termin: termin.id});
                termin.invitations = invitations;
            }
            console.log("Einladungen geladen.");
        }($scope.termine));
    });

}]);

terminplanerApp.controller("UserLoginController", ["$scope", "$location", "$routeParams", "Login",
    function ($scope, $location, $routeParams, Login) {
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
            $scope.user.id = user.id;
            $scope.user.username = user.username;
            $scope.user.email = user.email;
            $scope.user.authKey = user.authKey;
            $scope.updateHttpAuthHeaders();
            $location.path("/");
            $scope.alerts.push({type: "success", msg: "Willkommen, " + (user.username ? user.username : user.email)});
        });
        result.error(function (data, statusCode, headers) {
            $scope.alerts.push({type: "danger", msg: "Anmeldung fehlgeschlagen."});
            console.error("data = %o, statusCode = %o", data, statusCode);
        });
    };

    console.log("RP = %o", $routeParams);
    if ($routeParams.auth) {
        var result = Login.auth($routeParams.auth);
        result.success(function (user) {
            $scope.user.id = user.id;
            $scope.user.email = user.email;
            $scope.user.authKey = user.authKey;
            $scope.updateHttpAuthHeaders();
            $location.path("/termine");
            $scope.alerts.push({type: "success", msg: "Willkommen, " + (user.username ? user.username : user.email)});
        });
        result.error(function () {
            $location.path("/user/login");
        });
    }

}]);