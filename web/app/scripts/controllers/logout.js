app
  .controller('LogoutCtrl', [
    '$scope',
    '$http',
    '$location',
    'COMMON_CONST',
    function ($scope,
              $http,
              $location,
              COMMON_CONST) {

      // Logout
      $scope.logout = function () {
        $http({
          method: 'POST',
          url: '/app/logout',
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
          .success(function (data) {
            var result = angular.fromJson(data);
            if (!angular.isUndefined(result.success) && result.success) {
              $location.path('/login');
            } else {
              $scope.message = COMMON_CONST.CONNECTION_ERROR;
            }
          })
          .error(function (error) {
            $scope.message = COMMON_CONST.CONNECTION_ERROR;
          });
      };
    }]);
