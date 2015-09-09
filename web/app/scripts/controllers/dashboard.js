app
  .controller('DashboardCtrl', [
    '$scope',
    '$http',
    '$location',
    '$filter',
    '$sce',
    'blockUI',
    'COMMON_CONST',
    'AuthenFactory',
    'CommentFactory',
    'MemberFactory',
    function ($scope,
              $http,
              $location,
              $filter,
              $sce,
              blockUI,
              COMMON_CONST,
              AuthenFactory,
              CommentFactory,
              MemberFactory) {
      $scope.currentPage = 1;
      $scope.maxComment = COMMON_CONST.MAX_COMMENT;
      $scope.comments = [];
      $scope.message = "";
      $scope.member = {};

      // Display Html
      $scope.displayHtml = function (text) {
        return $sce.trustAsHtml(text);
      }

      /**
       * check Login information
       */
      $scope.getCommentList = function (page) {

        CommentFactory.dl(page)
          .success(function (data) {
            var result = angular.fromJson(data);

            // Update session Information if it null
            if(angular.isUndefined($scope.member.email) || $scope.member.email == '') {
              MemberFactory.set(result.session)
              $scope.member = MemberFactory.get();
            }

            if (result.success) {
              $scope.currentPage = page;
              $scope.comments = result.data;
            }
          })
          .error(function (error, status) {
            AuthenFactory.isAuthenticate(status);
          });
      };

      /*
       * Next Page
       */
      $scope.next = function () {
        $scope.getCommentList($scope.currentPage + 1);
      };

      /**
       * Back Page
       */
      $scope.previous = function () {
        $scope.getCommentList($scope.currentPage - 1);
      };

      $scope.startUp = function(){
        // Get All Post List by Current Page
        $scope.getCommentList($scope.currentPage);
      }

      $scope.startUp();
    }]);
