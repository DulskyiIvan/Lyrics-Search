package edu.geekhub.example.admin;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.model.UserDto;
import edu.geekhub.example.authentication.user.service.UserService;
import edu.geekhub.example.service.artist.model.Artist;
import edu.geekhub.example.service.artist.model.ArtistDto;
import edu.geekhub.example.service.artist.service.ArtistService;
import edu.geekhub.example.service.comments.service.CommentService;
import edu.geekhub.example.service.genre.model.Genre;
import edu.geekhub.example.service.genre.service.GenreService;
import edu.geekhub.example.service.lyric.model.Lyric;
import edu.geekhub.example.service.lyric.model.LyricDto;
import edu.geekhub.example.service.lyric.service.LyricService;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyricDto;
import edu.geekhub.example.service.proposeEditLyric.service.ProposeEditLyricService;
import edu.geekhub.example.service.role.model.Role;
import edu.geekhub.example.service.song.model.Song;
import edu.geekhub.example.service.song.model.SongDto;
import edu.geekhub.example.service.song.service.SongService;
import edu.geekhub.example.service.song.service.SongViewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final UserService userService;
    private final ArtistService artistService;
    private final SongService songService;
    private final SongViewService songViewService;
    private final LyricService lyricService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;
    private final ProposeEditLyricService proposeEditLyricService;

    public AdminService(UserService userService, ArtistService artistService,
        SongService songService, SongViewService songViewService, LyricService lyricService,
        GenreService genreService,
        CommentService commentService, PasswordEncoder passwordEncoder,
        ProposeEditLyricService proposeEditLyricService) {
        this.userService = userService;
        this.artistService = artistService;
        this.songService = songService;
        this.songViewService = songViewService;
        this.lyricService = lyricService;
        this.genreService = genreService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        this.proposeEditLyricService = proposeEditLyricService;
    }

    public Page<UserDto> getUsersDtoByUsername(Pageable pageable, String username) {
        return userService.getUsersDtoByUsername(username, pageable);
    }

    public Genre addGenre(Genre genre) {
        return genreService.addGenre(genre);
    }

    public UserDto updatePassword(UUID id, String password) {
        User user = userService.getUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        return userService.updateUser(user);
    }

    public UserDto updateRole(UUID userId, Role role) {
        return userService.setRole(userId, role.getTitle());
    }

    public Page<UserDto> getPageAllUsers(Pageable pageable) {
        return userService.getPageAllUsers(pageable);
    }

    public ArtistDto updateArtist(Artist artist) {
        return artistService.updateArtist(artist);
    }

    @Transactional
    public void deleteSongById(UUID id) {
        lyricService.deleteAllLyricBySongId(id);
        songViewService.deleteViewsBySongId(id);
        commentService.deleteAllCommentsBySongId(id);
        proposeEditLyricService.deleteProposeBySongId(id);
        songService.delete(id);
    }

    public void deleteGenre(Genre genre) {
        List<Song> songs = songService.getSongsByGenre(genre);
        if (songs.isEmpty()) {
            genreService.deleteGenre(genre.getId());
        } else {
            throw new RuntimeException("Failed to delete genre " + genre.getTitle());
        }
    }

    public void deleteArtist(Artist artist) {
        List<Song> songs = songService.getSongsByArtistId(artist.getId());
        if (songs.isEmpty()) {
            artistService.deleteArtist(artist.getId());
        } else {
            throw new RuntimeException("Failed to delete artist " + artist.getName());
        }
    }

    @Transactional
    public LyricDto addProposeToLyric(ProposeEditLyric proposeEditLyric, UUID songId) {
        Lyric lyric = new Lyric();
        lyric.setText(proposeEditLyric.getLyricText());
        LyricDto addedProposeLyric = lyricService.addLyric(proposeEditLyric.getUser(), lyric, songId);
        proposeEditLyricService.deletePropose(proposeEditLyric.getId());
        return addedProposeLyric;
    }

    public SongDto updateGenre(Song song) {
        return songService.updateGenre(song);
    }

    public Page<ProposeEditLyricDto> getAllProposesByOrderBySongTitle(Pageable pageable) {
        return proposeEditLyricService.getAllByOrderBySongTitle(pageable);
    }

    public Page<ProposeEditLyricDto> getAllProposesByCreatedAt(Pageable pageable) {
        return proposeEditLyricService.getAllByCreatedAt(pageable);
    }

    public void deletePropose(UUID id) {
        proposeEditLyricService.deletePropose(id);
    }
}
