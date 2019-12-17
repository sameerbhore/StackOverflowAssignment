Assumptions/Considerations:
1. Post canbe editied by respective owner only however this is not yet implemented in this code
2. Deletion of question should delete all related answers along with all comments
3. Comments are supported for 1 level only
4. Test cases are not written for all cases
5. at a time a user can post 1 question, answer or comment
6. We can have multi object APIs for migrating data from any other system
7. Designed model is considered as per separation of concerns
7. Generic solution should be something like below:
		All questions, Answers and comments should be treated as POSTS and in case of answer parent reference would be question's post_id
		For comment parent reference could be either answer's post_id or comment's post_id along with it's type make query performant
		Question's parent reference would be NULL
