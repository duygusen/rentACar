package com.tobeto.rentACar.controllers;

import com.tobeto.rentACar.core.services.JwtService;
import com.tobeto.rentACar.core.utilities.results.Result;
import com.tobeto.rentACar.services.abstracts.AuthCService;
import com.tobeto.rentACar.services.abstracts.UserService;
import com.tobeto.rentACar.services.dtos.user.request.*;
import com.tobeto.rentACar.services.dtos.user.response.GetAllUsersResponse;
import com.tobeto.rentACar.services.dtos.user.response.GetUserByIdResponse;
import com.tobeto.rentACar.services.dtos.user.response.GetUserByNameResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@CrossOrigin
public class UsersController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/add")
    public Result add(@RequestBody @Valid AddUserRequest request){
        return userService.add(request);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateUserRequest request){

        return userService.update(request);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteUserRequest request){
        return userService.delete(request);
    }

    @GetMapping("/getAll")
    public List<GetAllUsersResponse> getAll(){
        return userService.getAll();
    }

    @GetMapping("/getById/{id}")
    public GetUserByIdResponse getById(@PathVariable int id){
        return  userService.getById(id);
    }

    @GetMapping("/getProfile")
    public GetUserByNameResponse getProfile(HttpServletRequest request) {
        String tokenWithPrefix = request.getHeader("Authorization");
        String token = tokenWithPrefix.replace("Bearer ", "");
        String username = jwtService.extractUser(token);
        return userService.getByName(username);
    }

    @PutMapping("/updateProfile")
    public GetUserByNameResponse updateProfile(@RequestBody UpdateProfileRequest request, HttpServletRequest httpRequest) {
        String tokenWithPrefix = httpRequest.getHeader("Authorization");
        String token = tokenWithPrefix.replace("Bearer ", "");
        String username = jwtService.extractUser(token);


        //UpdateProfileRequest filteredRequest = new UpdateProfileRequest();
        request.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String newPassword = request.getPassword(); // Yeni şifreyi al
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt()); // Yeni şifreyi hashle
            request.setPassword(hashedPassword);
        }


        // Kullanıcı adına göre güncelleme yapılması için servis çağrısı
        return userService.updateProfile(username, request);
    }

}
