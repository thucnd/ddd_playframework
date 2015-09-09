app
  .controller('RegisterCtrl', [
    '$scope',
    '$rootScope',
    '$http',
    '$modalInstance',
    'MemberFactory',
    'COMMON_CONST',
    'REGISTER_CONST',
    function ($scope,
              $rootScope,
              $http,
              $modalInstance,
              MemberFactory,
              COMMON_CONST,
              REGISTER_CONST) {
      $scope.message = "";
      // Register new Member
      $scope.register = function (isValid) {
        $scope.message = "";
        if (isValid) {
          MemberFactory.create({
            'name': $scope.name,
            'email': $scope.email,
            'password': $scope.password
          })
            .success(function (data) {
              var result = angular.fromJson(data);
              if (!angular.isUndefined(result.success)) {
                if (result.success) {
                  $rootScope.$broadcast('registerSuccessMessage', REGISTER_CONST.ACCOUNT_CREATED_SUCCESS);
                  $modalInstance.dismiss('cancel');
                } else {
                  $scope.message = result.message;
                }
              } else {
                $scope.message = COMMON_CONST.CONNECTION_ERROR;
              }
            })
            .error(function (error) {
              $scope.message = COMMON_CONST.CONNECTION_ERROR;
            });
        }
      };

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };
    }]);
