package edu.geekhub.example.admin;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.model.UserDto;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.model.ArtistDto;
import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.lyric.model.LyricDto;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyricDto;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getUsersByUsername(
        @RequestParam("username") String username,
        @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserDto> usersPage = adminService.getUsersDtoByUsername(pageable, username);
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/all-users")
    public ResponseEntity<Page<UserDto>> getPageAllUsers(
        @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserDto> usersPage = adminService.getPageAllUsers(pageable);
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/propose-edit/all-proposes")
    public ResponseEntity<Page<ProposeEditLyricDto>> getPageProposesEditLyric(
        @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("songTitle").descending());
        Page<ProposeEditLyricDto> proposeEditLyricPage = adminService.getAllProposesByOrderBySongTitle(
            pageable);
        return ResponseEntity.ok(proposeEditLyricPage);
    }

    @GetMapping("/propose-edit/all-by-date")
    public ResponseEntity<Page<ProposeEditLyricDto>> getPageProposesEditLyricByDate(
        @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<ProposeEditLyricDto> proposeEditLyricPage = adminService.getAllProposesByCreatedAt(
            pageable);
        return ResponseEntity.ok(proposeEditLyricPage);
    }

    @DeleteMapping("/propose-edit/delete")
    public ResponseEntity<Void> deletePropose(@RequestBody ProposeEditLyricDto proposeEdit) {
        adminService.deletePropose(proposeEdit.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/add-genre")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        if (genre.getTitle() != null && !genre.getTitle().isBlank()) {
            Genre addedGenre = adminService.addGenre(genre);
            if (addedGenre != null) {
                return ResponseEntity.ok(addedGenre);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/add-lyric")
    public ResponseEntity<LyricDto> addProposeToLyric(
        @RequestBody ProposeEditLyric proposeEditLyric,
        @RequestParam("songId")
        UUID songId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        proposeEditLyric.setUser(user);
        if (proposeEditLyric.getLyricText() != null && !proposeEditLyric.getLyricText().isBlank()) {

            LyricDto addedLyric = adminService.addProposeToLyric(proposeEditLyric, songId);
            if (addedLyric != null) {
                return ResponseEntity.ok(addedLyric);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update-user-password")
    public ResponseEntity<UserDto> updateUserPassword(@RequestBody User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            UserDto updatedUser = adminService.updatePassword(user.getId(), user.getPassword());
            if (updatedUser == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update-user-role")
    public ResponseEntity<UserDto> updateUserRole(@RequestBody User user) {
        if (user.getRole() != null) {
            UserDto updatedUser = adminService.updateRole(user.getId(), user.getRole());
            if (updatedUser == null || updatedUser.getRole() != null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update-artist")
    public ResponseEntity<ArtistDto> updateArtistDescription(@RequestBody Artist artist) {
        if (artist.getDescription() != null) {
            ArtistDto updatedArtist = adminService.updateArtist(artist);
            if (updatedArtist == null || updatedArtist.getDescription() == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(updatedArtist);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/song/update-genre")
    public ResponseEntity<SongDto> updateSongGenre(@RequestBody Song song) {
        if (song.getGenre() != null) {
            SongDto updatedSong = adminService.updateGenre(song);
            if (updatedSong == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(updatedSong);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete-genre")
    public ResponseEntity<Void> deleteGenre(@RequestBody Genre genre) {
        adminService.deleteGenre(genre);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete-song")
    @ResponseBody
    public ResponseEntity<Void> deleteSongById(@RequestBody Song song) {
        adminService.deleteSongById(song.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete-artist")
    @ResponseBody
    public ResponseEntity<Void> deleteArtistById(@RequestBody Artist artist) {
        adminService.deleteArtist(artist);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
