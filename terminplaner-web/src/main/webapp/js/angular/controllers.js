"use strict";

terminplanerApp.controller("BodyController", ["$scope", "$location", function ($scope, $location) {
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

}]);

terminplanerApp.controller("TerminNeuController", ["$scope", "Termine", "$location", function ($scope, Termine, $location) {
    $scope.submit = function () {
        Termine.save($scope.termin, function () {
                $scope.alerts.push({type: "success", msg: "Termin erfolgreich angelegt."});
                $scope.termin = new Termine();
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