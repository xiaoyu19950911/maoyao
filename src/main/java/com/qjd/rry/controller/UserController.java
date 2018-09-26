package com.qjd.rry.controller;

import com.qjd.rry.annotation.DefaultPage;
import com.qjd.rry.request.*;
import com.qjd.rry.response.*;
import com.qjd.rry.response.V0.*;
import com.qjd.rry.service.UserService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value = "user", description = "用户相关接口")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/queryuserinfo")
    @ApiOperation("获取当前用户信息")
    public Result<UserInfoResponse> getUserInfo() {
        return userService.queryUserInfo();
    }

    @GetMapping("/queryuserliveroom")
    @ApiOperation("获取用户直播间信息")
    public Result<UserLiveRoomResponse> getUserLiveRoom(@Valid @ModelAttribute UserLiveRoomGetRequest request,BindingResult bindingResult) {
        return userService.queryUserLiveRoom(request.getUserId());
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("用户详情_获取用户详情(web_admin)")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<UserInfoGetResponse> getUserInfo(@Valid @ModelAttribute UserInfoGetRequest request,BindingResult bindingResult) {
        return userService.getUserInfo(request.getUserId());
    }

    @PostMapping("/updateUser")
    @ApiOperation("web_用户详情_更改用户信息")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result updateUser(@Valid @RequestBody UserUpdateRequest request, BindingResult bindingResult) throws Exception {
        return userService.updateUser(request);
    }

    @GetMapping("/queryattentionteacherlist")
    @ApiOperation("获取关注讲师列表")
    public Result<ListResponse<TeacherInfo>> getAttentionTeacherList(@Valid @ModelAttribute AttentionTeacherListGetRequest request, BindingResult bindingResult,  Pageable pageable) {
        return userService.queryAttentionTeacherList(request.getUserId(),pageable);
    }

    @PostMapping("/modifyattentionteacherlist")
    @ApiOperation("设置关注状态")
    public Result<UserLiveRoomResponse> updateAttentionTeacherList(@Valid @RequestBody AttentionTeacherListUpdateRequest request,BindingResult bindingResult) {
        return userService.modifyAttentionTeacherList(request);
    }

    @GetMapping("/querycategoryteeacherlist")
    @ApiOperation("根据分类获取讲师列表(不传id则查询所有讲师列表)(app)")
    public Result<ListResponse<TeacherInfo>> getCategoryTeacherList(@Valid @ModelAttribute CategoryTeacherListGetRequest request,BindingResult bindingResult,Pageable pageable){
        return userService.queryCategoryTeacherList(request.getSearchInfo(),request.getCategoryId(),pageable);
    }

    @GetMapping("/getTeacherListByCategory")
    @ApiOperation("分类管理_根据分类获取讲师列表(不传参数则查询所有讲师列表)(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<ListResponse<WebTeacherInfo>> getTeacherListByCategoryOld(@Valid @ModelAttribute TeacherListByCategoryOldGetRequest request,BindingResult bindingResult, Pageable pageable){
        return userService.getTeacherListByCategory(request.getCategoryId(),request.getStatus(),request.getIsBanner(),request.getRole(),pageable);
    }

    @GetMapping("/getWebTeacherListByCategory")
    @ApiOperation("分类管理_根据分类获取讲师列表(不传参数则查询所有讲师列表)(web_root)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<ListResponse<WebTeacherInfo>> getTeacherListByCategory(@Valid @ModelAttribute TeacherListByCategoryGetRequest request,BindingResult bindingResult,Pageable pageable){
        return userService.getTeacherListByCategory(request,pageable);
    }

    @GetMapping("/getCategoryList")
    @ApiOperation("用户管理_设置分类_查看讲师分类(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<List<CategoryListGetResponse>> getCategoryList(@Valid @ModelAttribute CategoryListGetRequest request,BindingResult bindingResult){
        return userService.getCategoryList(request.getUserId());
    }

    @GetMapping("/queryArtistList")
    @ApiOperation("查看更多-根据分类获取已认证艺术家列表")
    public Result<ListResponse<TeacherInfo>> getArtistList(@Valid @ModelAttribute ArtistListGetRequest request,BindingResult bindingResult,@PageableDefault(sort = {"certificationTime"},direction = Sort.Direction.DESC) Pageable pageable){
        return userService.queryArtistList(request,pageable);
    }

    /*@GetMapping("/queryPartArtistList")
    @ApiOperation("学艺-获取已认证艺术家列表")
    public Result<ListResponse<TeacherInfo>> getPartArtistList(@PageableDefault Pageable pageable){
        return userService.queryPartArtistList(pageable);
    }*/

    /*@GetMapping("/querytransactioninfolist")
    @ApiOperation("获取收入支出信息列表(待修改-此接口暂时先不要调用)")
    @ApiImplicitParam(name = "type", value = "1、查询收入列表；2、查询支出列表", defaultValue = "1", paramType = "query", dataType = "Long")
    public Result<ListResponse<TransactionInfoResponse>> getTransactionInfoList(@RequestParam Integer type,@PageableDefault Pageable pageable) {
        return userService.queryTransactionInfoList(type, pageable);
    }*/

    /*@GetMapping("/getIncomeResponse")
    @ApiOperation("获取收支列表")
    @ApiImplicitParam(name = "typeList",value = "类型（1、收入；2、支出）",paramType = "query")
    public Result<ListResponse<IncomeGetResponse>> getIncomeResponse(@Valid @PageableDefault  @RequestParam List<Integer> typeList, Pageable pageable) {
        return userService.getIncomeResponse(typeList,pageable);
    }*/

    @GetMapping("/getIncomeResponse")
    @ApiOperation("获取收支列表")
    public Result<ListResponse<IncomeGetResponse>> getIncomeResponse(@Valid @ModelAttribute IncomeResponseGetRequest request,BindingResult bindingResult,Pageable pageable) {
        return userService.getIncomeResponse(request,pageable);
    }

    @GetMapping("/getAllIncome")
    @ApiOperation("web_admin_获取总的收入列表")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @DefaultPage
    public Result<ListResponse<AllIncomeGetResponse>> getAllIncomeResponse(@Valid @ModelAttribute AllIncomeGetRequest request, Pageable pageable,BindingResult bindingResult) {
        return userService.getAllIncomeResponse(request,pageable);
    }

    @GetMapping("/getIncomeSituation")
    @ApiOperation("web_admin_获取收入概况")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<IncomeSituationGetResponse> getIncomeSituation(@Valid @ModelAttribute IncomeSituationGetRequest request,BindingResult bindingResult) {
        return userService.getIncomeSituation(request);
    }

    @GetMapping("/getUserIncomeSituation")
    @ApiOperation("web_admin_普通用户获取收入概况")
    public Result<UserIncomeSituationGetResponse> getUserIncomeSituation(@Valid @ModelAttribute UserIncomeSituationGetRequest request,BindingResult bindingResult) {
        return userService.getUserIncomeSituation(request);
    }

    @GetMapping("/getIncomeInfo")
    @ApiOperation("web_admin_获取收入详情")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<IncomeInfoGetResponse> getIncomeInfo(@Valid @ModelAttribute IncomeInfoGetRequest request,BindingResult bindingResult) {
        return userService.getIncomeInfo(request.getOrderItemId());
    }

    @GetMapping("/getExpenditureResponse")
    @ApiOperation("获取支出列表")
    @ApiIgnore
    public Result<ListResponse<ExpenditureGetResponse>> getExpenditureResponse(Pageable pageable) {
        return userService.getExpenditureResponse(pageable);
    }

    @GetMapping("/getUserAccountInfo")
    @ApiOperation("获取用户账户信息")
    public Result<UserAccountInfoResponse> getUserAccountInfo() {
        return userService.queryUserAccountInfo();
    }

    @PostMapping("/modifyuserinfo")
    @ApiOperation("修改个人信息")
    public Result<UserInfoResponse> updateUserInfo(@Valid @RequestBody UserInfoRequest request, BindingResult bindingResult) throws Exception {
        return userService.modifyUserInfo(request);
    }

    @PostMapping("/modifyAccountInfo")
    @ApiOperation("修改密码")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AGENTS','ROLE_AGENTS_2')")
    public Result modifyAccountInfo(@Valid @RequestBody AccountInfoModifyRequest request, BindingResult bindingResult) throws Exception {
        return userService.modifyAccountInfo(request);
    }

    @PostMapping("/modifyarteinfo")
    @ApiOperation("设置艺术家状态(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_AGENTS')")
    public Result updateArteInfo(@Valid @RequestBody ArteRequest request ,BindingResult bindingResult){
        return userService.modifiableInfo(request);
    }

    @PostMapping("/deleteArtistInfoInBatch")
    @ApiOperation("批量删除艺术家状态(web_root)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result deleteArtistInfoInBatch(@Valid @RequestBody List<ArteRequest> request ,BindingResult bindingResult) throws Exception {
        return userService.deleteArtistInfoInBatch(request);
    }

    @PostMapping("/updateArteCategory")
    @ApiOperation("设置艺术家分类(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result updateArteCategory(@Valid @RequestBody ArteCategoryUpdateRequest request , BindingResult bindingResult) {
        return userService.updateArteCategory(request);
    }

    @GetMapping("/queryownaccountlist")
    @ApiOperation("查询自有账号列表")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<ListResponse<OwnAccount>> getOwnAccountList(@Valid @ModelAttribute OwnAccountListGetRequest request,BindingResult bindingResult,Pageable pageable) {
        return userService.queryOwnAccountList(request.getSearchInfo(),pageable);
    }

    @GetMapping("/queryagentslist")
    @ApiOperation("代理商列表查询")
    @PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_AGENTS')")
    public Result<ListResponse<Agents>> getAgentsList(@Valid @ModelAttribute AgentsListGetRequest request,BindingResult bindingResult, Pageable pageable) {
        return userService.queryAgentsList(request,pageable);
    }

    @GetMapping("/queryinvitationartistlist")
    @ApiOperation("查询已邀请艺术家列表")
    @PreAuthorize("hasAnyRole('ROLE_AGENTS','ROLE_AGENTS_2','ROLE_ROOT')")
    public Result<ListResponse<InvitationArtistInfo>> getInvitationArtistList(@Valid @ModelAttribute InvitationArtistListGetRequest request,Pageable pageable) {
        return userService.queryInvitationArtistList(request,pageable);
    }

}
