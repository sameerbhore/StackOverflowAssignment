const Question = function (ques, userId) {
    this.ques = ques;
    this.userId = userId;
    this.Id = Math.round(Math.random() * 1000);
    this.ansCnt =0;
    this.createTime = new Date().getMinutes();
  };

  const Answer = function (ans, userId,qID) {
    this.ans = ans;
    this.userId = userId;
    this.Id =  Math.round(Math.random() * 1000);
    this.qId = qID;
    this.commentCnt =0;
    this.createTime = new Date().getMinutes();
  };

var questionPortal = angular.module("questionPortal", ['ngRoute']);
questionPortal.config(['$routeProvider', function($routeProvider){
    $routeProvider
    .when("/:quesId", {
      templateUrl: "./answer.html"
    })
    .when("/", {
        templateUrl: "./question.html"
      })
      .otherwise({redirectTo:'/'}) 
  }]);
  
questionPortal.controller("mainPageCtrl",['$scope',
function($scope){
    $scope.questionArray = [];
    $scope.allAnswerArray=[];


    $scope.addToDb = function(ques, logedinuser){
		//CALL REST END POINT FOR POST/PUT/DELETE depending on the operation
        var Qs = new Question(ques,logedinuser);
        $scope.questionArray.push(Qs);             
    }
}
]);

questionPortal.controller("quesController",['$scope', '$routeParams',
function($scope, $routeParams){
$scope.logedinuser ='Sameer';  //need to get loggedin user from session
$scope.Question = '';

$scope.addQuestion = function(){
    $scope.addToDb($scope.Question,$scope.logedinuser);
}
}
]);

questionPortal.controller("answerController",['$scope', '$routeParams',
function($scope, $routeParams){

    $scope.getQuestion = function(quesId){
        $scope.questionArray.forEach(function (quesObj){
            if(quesObj.Id == quesId){
                $scope.question =  quesObj.ques;
            }
        })
    }
    $scope.getAnswers = function(quesId){
        let currentAnswers = [];
        $scope.allAnswerArray.forEach(function (answer){
            if(answer.qId == quesId){
                currentAnswers.push(answer);
            }
        })
        return currentAnswers;
    }
   $scope.getQuestion($routeParams.quesId);

    $scope.currentAnswers = $scope.getAnswers($routeParams.quesId);  

   $scope.addAnswer = function(){
        var ans = new Answer($scope.Answer,$scope.logedinuser,$routeParams.quesId);
        $scope.allAnswerArray.push(ans);
        $scope.currentAnswers = $scope.getAnswers($routeParams.quesId);         
   }
}
]);
