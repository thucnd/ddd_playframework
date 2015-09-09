app
    .factory('CommentFactory', ['$http', function($http){
        var urlBase = '/app';

        return{
            dl: function(page){
               return $http({
                    method: 'GET',
                    url: urlBase + '/commentList',
                    params: {
                        'page': page
                    }
                });
            },
            create: function(param){
                return $http({
                    method: 'POST',
                    url: urlBase + '/createComment',
                    data: $.param(param),
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            }
        }
    }]);
