package gitbucket.core.controller.api

import gitbucket.core.api.CreateAWebHook
import gitbucket.core.controller.ControllerBase
import gitbucket.core.model.{WebHook, WebHookContentType}
import gitbucket.core.service.{AccountService, RepositoryCreationService, RepositoryService, WebHookService}
import gitbucket.core.util.{
  GroupManagerAuthenticator,
  OwnerAuthenticator,
  ReadableUsersAuthenticator,
  ReferrerAuthenticator,
  UsersAuthenticator,
  WritableUsersAuthenticator
}
import gitbucket.core.util.Implicits._

trait ApiWebHookService extends ControllerBase {
  self: RepositoryService
    with RepositoryCreationService
    with AccountService
    with OwnerAuthenticator
    with UsersAuthenticator
    with GroupManagerAuthenticator
    with ReferrerAuthenticator
    with ReadableUsersAuthenticator
    with WritableUsersAuthenticator
    with WebHookService =>

  post("/api/v3/repos/:owner/:repo/hooks") {
//    val owner = context.loginAccount.get.userName
    val owner = params("owner")
    val repo = params("repo")
    val hookDetails = extractFromJsonBody[CreateAWebHook].get
    println(hookDetails)
    addWebHook(owner, repo, hookDetails.url, Seq(WebHook.PullRequest).toSet, WebHookContentType.FORM, Option.empty)
  }

}
