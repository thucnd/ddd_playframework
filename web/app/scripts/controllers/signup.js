app
    .controller('SignUpCtrl', [
        '$scope',
        '$http',
        '$modal',
        function ($scope,
                  $http,
                  $modal) {

            $scope.showSignUp = function (size) {
                var modalInstance = $modal.open({
                    animation: $scope.animationsEnabled,
                    templateUrl: 'views/signup/form.html',
                    controller: 'RegisterCtrl',
                    size: size,
                    resolve: {
                        items: function () {
                            return $scope.items;
                        }
                    }
                });
            };
        }]);

