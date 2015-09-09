app
  .controller('LoginCtrl', [
    '$scope',
    '$http',
    '$location',
    'MemberFactory',
    'COMMON_CONST',
    function ($scope,
              $http,
              $location,
              MemberFactory,
              COMMON_CONST) {

      $scope.message = "";
      $scope.successMessage = "";

      // Get Success Message from Register Controller
      $scope.$on('registerSuccessMessage', function (event, successMessage) {
        $scope.successMessage = successMessage;
      });

      // Login in
      $scope.login = function (isValid) {
        $scope.message = "";
        $scope.successMessage = "";
        if (isValid) {
          MemberFactory.authenticate({
            'email': $scope.email,
            'password': $scope.password
          })
            .success(function (data) {
              var result = angular.fromJson(data);
              if (!angular.isUndefined(result.success)) {
                if (result.success) {
                  MemberFactory.set(result.data);
                  $location.path('/');
                } else {
                  $scope.message = (result.message !== "") ? result.message : "";
                }
              } else {
                $scope.message = COMMON_CONST.LOGIN_ERROR;
              }
            })
            .error(function (error) {
              $scope.message = COMMON_CONST.CONNECTION_ERROR;
            });
        }
      };
    }]);
